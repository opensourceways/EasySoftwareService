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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageDetailUrlVo {
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

    /**
     * constructor.
     *
     * @param cveDetailUrl cve detail url
     * @param prDetailUrl pr detail url
     * @param issueDetailUrl issue detail url
     * @param versionDetailUrl version detail url
     */
    public PackageDetailUrlVo(String cveDetailUrl, String prDetailUrl, String issueDetailUrl, String versionDetailUrl) {
        this.setCveDetailUrl(cveDetailUrl);
        this.setPrDetailUrl(prDetailUrl);
        this.setIssueDetailUrl(issueDetailUrl);
        this.setVersionDetailUrl(versionDetailUrl);
    }

    /**
     * constructor.
     *
     */
    public PackageDetailUrlVo() {
    }
}
