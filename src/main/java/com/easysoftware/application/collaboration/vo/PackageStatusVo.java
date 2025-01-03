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

package com.easysoftware.application.collaboration.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageStatusVo {
    /**
     * Name of the package. Restricted by length and character pattern.
     */
    private String repo;

    /**
     * repo kind.
     */
    private String kind;

    /**
     * repo status.
     */
    private String status;

    /**
     * sig name.
     */
    private String sigName;

    /**
     * cve status.
     */
    private String cveStatus;

    /**
     * issue status.
     */
    private String issueStatus;

    /**
     * pr status.
     */
    private String prStatus;

    /**
     * contributor status.
     */
    private String contributorStatus;

    /**
     * org status.
     */
    private String orgStatus;

    /**
     * version status.
     */
    private String versionStatus;

    /**
     * pkg version detail.
     */
    private List<PackageVersionVo> versionDetail;

    /**
     * suggestions list.
     */
    private List<String> suggestions;

    /**
     * level.
     */
    private String level;

    /**
     * cve detail url.
     */
    private String cveDetailUrl;

    /**
     * pr detail url.
     */
    private String prDetailUrl;

    /**
     * issue detail url.
     */
    private String issueDetailUrl;

    /**
     * version detail url.
     */
    private String versionDetailUrl;
}
