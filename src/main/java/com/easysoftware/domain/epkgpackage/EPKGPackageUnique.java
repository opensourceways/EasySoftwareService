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

package com.easysoftware.domain.epkgpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EPKGPackageUnique {
    /**
     * Name of the entity.
     */
    private String name;

    /**
     * Version of the entity.
     */
    private String version;

    /**
     * Operating system for which the entity is intended.
     */
    private String os;

    /**
     * Architecture of the entity.
     */
    private String arch;

    /**
     * Category to which the entity belongs.
     */
    private String category;

    /**
     * Timestamp indicating when the entity was last updated in the EPKG system.
     */
    private String epkgUpdateAt;


}
