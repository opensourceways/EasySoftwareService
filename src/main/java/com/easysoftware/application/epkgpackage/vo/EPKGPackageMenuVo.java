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

package com.easysoftware.application.epkgpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageMenuVo {
    /**
     * Name of the package.
     */
    private String name;

    /**
     * Unique identifier for the package.
     */
    private String id;

    /**
     * Version of the package.
     */
    private String version;

    /**
     * Operating system compatibility.
     */
    private String os;

    /**
     * Architecture type required by the package.
     */
    private String arch;

    /**
     * Category of the package.
     */
    private String category;

    /**
     * Update timestamp for the package.
     */
    private String rpmUpdateAt;

    /**
     * Source repository URL.
     */
    private String srcRepo;

    /**
     * Size of the package.
     */
    private String rpmSize;

    /**
     * Binary download URL.
     */
    private String binDownloadUrl;

    /**
     * Package identifier.
     */
    private String pkgId;

    /**
     * Subpath information.
     */
    private String subPath;

    /**
     * License.
     */
    private String license;

}
