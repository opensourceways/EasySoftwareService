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
package com.easysoftware.application.fieldpkg.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommandPkgVo {
     /**
     * Operating system.
     */
    private String os;

    /**
     * Architecture.
     */
    private String arch;

    /**
     * Name.
     */
    private String name;

    /**
     * Version.
     */
    private String version;

    /**
     * Category.
     */
    private String category;

    /**
     * Tags associated with the entity.
     */
    private String tags;

    /**
     * Description.
     */
    private String description;

    /**
     * maintainers.
     */
    private String maintainers;
}