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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easysoftware.application.collaboration.vo.PackageStatusVO;
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
    public static List<PackageStatusVO> toDetail(final JsonNode hits) {
        List<PackageStatusVO> pkgs = new ArrayList<>();
        for (JsonNode hit : hits) {
            PackageStatusVO pkg = new PackageStatusVO();
            JsonNode source = hit.path("_source");
            pkg.setIssueStatus(source.path("issue").path("status").asText());
            pkg.setPrStatus(source.path("package_update").path("status").asText());
            pkg.setCveStatus(source.path("cve").path("status").asText());
            pkg.setVersionStatus(source.path("package_version").path("status").asText());
            pkg.setOrgStatus(source.path("company").path("status").asText());
            pkg.setContributorStatus(source.path("participant").path("status").asText());
            pkg.setRepo(source.path("repo").asText());
            pkg.setKind(source.path("kind").asText());
            pkg.setSigName(source.path("sig_names").asText());
            pkg.setStatus(source.path("status").asText());
            pkgs.add(pkg);
        }
        return pkgs;
    }
}
