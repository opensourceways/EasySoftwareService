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

package com.easysoftware.application.fieldpkg.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldPkgSearchCondition {
    /**
     * Page number within the specified range.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    @NotNull
    private Integer pageNum = 1;

    /**
     * Page size within the specified range.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    @NotNull
    private Integer pageSize = 10;

    /**
     * Operating system (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String os;

    /**
     * Architecture (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String arch;

    /**
     * Category (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String category;

    /**
     * Name (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String name;

    /**
     * Time order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String timeOrder;

    /**
     * Name order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String nameOrder;
}
