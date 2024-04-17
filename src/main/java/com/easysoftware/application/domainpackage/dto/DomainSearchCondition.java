package com.easysoftware.application.domainpackage.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.exception.enumvalid.DomainNameEnum;
import com.easysoftware.common.exception.enumvalid.EnumValue;
import com.easysoftware.common.exception.enumvalid.TimeOrderEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainSearchCondition {
    @EnumValue(enumClass = DomainNameEnum.class, enumMethod = "isValidCategory")
    private String name;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    // 软件包名称
    private String entity;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String os;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String arch;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String category;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String version;

    @EnumValue(enumClass = TimeOrderEnum.class, enumMethod = "isValidCategory")
    private String timeOrder = "desc";

    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;
}

