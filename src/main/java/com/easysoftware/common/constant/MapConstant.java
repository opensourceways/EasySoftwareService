
/* Copyright (c) 2024 openEuler Community
 EasySoftwareInput is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.common.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MapConstant {
    // Private constructor to prevent instantiation of the utility class
    private MapConstant() {
        // private constructor to hide the implicit public one
        throw new AssertionError("ClientUtil class cannot be instantiated.");
    }

    /**
     * package status metirc map.
     */
    public static final Map<String, String> METRIC_MAP;

    static {
        Map<String, String> item = new HashMap<>();
        item.put("cveStatus", "collaboration.cve.status");
        item.put("prStatus", "collaboration.package_update.status");
        item.put("issueStatus", "collaboration.issue.status");
        item.put("versionStatus", "collaboration.package_version.status");
        item.put("orgStatus", "collaboration.company.status");
        item.put("contributorStatus", "collaboration.participant.status");
        item.put("sigName", "sig_names");
        item.put("status", "collaboration.status");
        item.put("repo", "repo");
        item.put("kind", "kind");
        METRIC_MAP = Collections.unmodifiableMap(item);
    }

}
