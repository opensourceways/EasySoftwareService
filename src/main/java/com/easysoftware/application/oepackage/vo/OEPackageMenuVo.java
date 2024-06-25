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
package com.easysoftware.application.oepackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OEPackageMenuVo {
    /**
     * Name of the OEpackage.
     */
    private String name;

    /**
     * ID of the OEpackage.
     */
    private String id;

    /**
     * Version of the OEpackage.
     */
    private String version;

    /**
     * Operating system for which the package is intended.
     */
    private String os;

    /**
     * Architecture of the package.
     */
    private String arch;

    /**
     * Category to which the package belongs.
     */
    private String category;

    /**
     * Timestamp of the last OEpackage update.
     */
    private String rpmUpdateAt;

    /**
     * Source repository for the package.
     */
    private String srcRepo;

    /**
     * Size of the OEpackage package.
     */
    private String rpmSize;

    /**
     * Binary download URL for the package.
     */
    private String binDownloadUrl;

    /**
     * Package ID.
     */
    private String pkgId;

    /**
     * Subpath of the package.
     */
    private String subPath;

    /**
     * License.
     */
    private String license;
}
