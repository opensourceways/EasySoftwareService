/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.infrastructure.collaboration.gatewayimpl.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easysoftware.application.collaboration.vo.PackageStatusVo;
import com.easysoftware.application.collaboration.vo.PackageVersionVo;
import com.easysoftware.common.constant.PackageConstant;
import com.fasterxml.jackson.databind.JsonNode;

public final class PackageStatusConverter {
    // Private constructor to prevent instantiation of the utility class
    private PackageStatusConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate PackageStatusConverter class");
    }

    /**
     * Logger instance for PackageStatusConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageStatusConverter.class);

    /**
     * Convert JsonNode to a list of PackageStatusVO
     * objects.
     *
     * @param hits JsonNode to convert
     * @return A list of PackageStatusVO objects
     */
    public static List<PackageStatusVo> toDetail(final JsonNode hits) {
        List<PackageStatusVo> pkgs = new ArrayList<>();
        for (JsonNode hit : hits) {
            PackageStatusVo pkg = new PackageStatusVo();
            JsonNode source = hit.path("_source");
            pkg.setRepo(source.path("repo").asText());
            pkg.setKind(source.path("kind").asText());
            pkg.setSigName(source.path("sig_names").asText());

            JsonNode collaboration = source.path("collaboration");
            pkg.setIssueStatus(collaboration.path("issue").path("status").asText());
            pkg.setPrStatus(collaboration.path("package_update").path("status").asText());
            pkg.setCveStatus(collaboration.path("cve").path("status").asText());
            pkg.setVersionStatus(collaboration.path("package_version").path("status").asText());
            pkg.setOrgStatus(collaboration.path("company").path("status").asText());
            pkg.setContributorStatus(collaboration.path("participant").path("status").asText());
            pkg.setStatus(collaboration.path("status").asText());
            JsonNode upVersion = collaboration.path("package_version").path("up_version");
            JsonNode eulerVersion = collaboration.path("package_version").path("version");
            List<PackageVersionVo> version = merged(upVersion, eulerVersion);
            pkg.setVersionDetail(version);
            List<String> suggestions = fixSuggestion(pkg);
            pkg.setSuggestions(suggestions);
            pkgs.add(pkg);
        }
        return pkgs;
    }

    /**
     * Get fix suggestion.
     *
     * @param pkg package info
     * @return A list of fix suggestion
     */
    public static List<String> fixSuggestion(PackageStatusVo pkg) {
        List<String> suggestions = new ArrayList<>();
            if (PackageConstant.CVE_ALL_NO_FIXED.equals(pkg.getCveStatus())
                    || PackageConstant.CVE_SOME_FIXED.equals(pkg.getCveStatus())) {
                suggestions.add(PackageConstant.CVE_SUGGESTION);
            }
            if (PackageConstant.OUTDATED_VERSION.equals(pkg.getVersionStatus())) {
                suggestions.add(PackageConstant.VERSION_SUGGESTION);
            }
        return suggestions;
    }

    /**
     * Merge upVersion and eulerVersion to a list of PackageVersionVo
     * objects.
     *
     * @param upVersion JsonNode to convert
     * @param eulerVersion JsonNode to convert
     * @return A list of PackageVersionVo objects
     */
    public static List<PackageVersionVo> merged(JsonNode upVersion, JsonNode eulerVersion) {
        Map<String, PackageVersionVo> itemMap = new HashMap<>();
        for (JsonNode info : eulerVersion) {
            String name = info.path("pkg_name").asText();
            String version = info.path("pkg_version").asText();
            PackageVersionVo pkg = new PackageVersionVo(name, version, null);
            itemMap.put(name, pkg);
        }

        for (JsonNode info : upVersion) {
            String name = info.path("pkg_name").asText();
            String version = info.path("pkg_version").asText();
            PackageVersionVo pkg = itemMap.getOrDefault(name, new PackageVersionVo(name, null, version));
            pkg.setUpVersion(version);
            itemMap.put(name, pkg);
        }
        return new ArrayList<>(itemMap.values());
    }
}
