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

package com.easysoftware.application.filedapplication.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDetailSearchCondition {
    /**
     * RPM package ID (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String rpmPkgId;

    /**
     * EPKG package ID (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String epkgPkgId;

    /**
     * Application package ID (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String appPkgId;

}
