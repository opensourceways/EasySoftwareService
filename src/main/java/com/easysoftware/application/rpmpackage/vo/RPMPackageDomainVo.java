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

package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageDomainVo {
    /**
     * Category of the package.
     */
    private String category;

    /**
     * Description of the package.
     */
    private String description;

    /**
     * Name of the package.
     */
    private String name;

    /**
     * List of tags associated with the package.
     */
    private List<String> tags;

    /**
     * ID of the package.
     */
    private String id;

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
