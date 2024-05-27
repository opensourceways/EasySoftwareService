/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.application.applicationpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageMenuVo {
    /**
     * Name of the software package.
     */
    private String name;

    /**
     * Operating system support information for the software package.
     */
    private String osSupport;

    /**
     * Operating system compatibility for the software package.
     */
    private String os;

    /**
     * Application version of the software package.
     */
    private String appVer;

    /**
     * Unique identifier for the software package.
     */
    private String id;

    /**
     * Category of the software package.
     */
    private String category;

    /**
     * Architecture requirements for the software package.
     */
    private String arch;

    /**
     * Type or category of the software package.
     */
    private String type;

    /**
     * Package ID of the software package.
     */
    private String pkgId;

    /**
     * Tags associated with the software package.
     */
    private String imageTags;

    /**
     * Download details for the software package.
     */
    private String download;

}
