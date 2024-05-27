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

package com.easysoftware.domain.externalos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalOsUnique {
    /**
     * Name of the originating operating system.
     */
    private String originOsName;

    /**
     * Version of the originating operating system.
     */
    private String originOsVer;

    /**
     * Name of the package in the originating operating system.
     */
    private String originPkg;

    /**
     * Name of the target operating system.
     */
    private String targetOsName;

    /**
     * Version of the target operating system.
     */
    private String targetOsVer;

    /**
     * Name of the package in the target operating system.
     */
    private String targetPkg;

}
