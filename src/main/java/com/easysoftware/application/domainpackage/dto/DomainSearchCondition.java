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

    @Size(max = 50)
    // 软件包名称
    private String entity;

    @Size(max = 50)
    private String os;

    @Size(max = 50)
    private String arch;

    @Size(max = 50)
    private String category;

    @EnumValue(enumClass = TimeOrderEnum.class, enumMethod = "isValidCategory")
    private String timeOrder;

    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;
}

