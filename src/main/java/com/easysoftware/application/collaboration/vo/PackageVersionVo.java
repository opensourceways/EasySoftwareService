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
public class PackageVersionVo {
    /**
     * Name of the package.
     */
    private String pkg;

    /**
     * upstream version of the package.
     */
    private String upVersion;

    /**
     * euler version of the package.
     */
    private String eulerVersion;

    /**
     * constructor.
     *
     * @param pkgName pkg name
     * @param eulerVersion euler version
     * @param upVersion upstream version
     */
    public PackageVersionVo(String pkgName, String eulerVersion, String upVersion) {
        this.setPkg(pkgName);
        this.setEulerVersion(eulerVersion);
        this.setUpVersion(upVersion);
    }

    /**
     * constructor.
     *
     */
    public PackageVersionVo() {
    }
}
