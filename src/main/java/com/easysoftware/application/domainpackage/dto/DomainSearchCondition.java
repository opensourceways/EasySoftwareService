package com.easysoftware.application.domainpackage.dto;

import org.hibernate.validator.constraints.Range;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.exception.enumvalid.DomainNameEnum;
import com.easysoftware.common.exception.enumvalid.EnumValue;
import com.easysoftware.common.exception.enumvalid.TimeOrderEnum;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainSearchCondition {
    /**
     * Domain name with an enum value from DomainNameEnum.isValidCategory.
     */
    @EnumValue(enumClass = DomainNameEnum.class, enumMethod = "isValidCategory")
    private String name;

    /**
     * Software package entity with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    // 软件包名称
    private String entity;

    /**
     * Operating system with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String os;

    /**
     * Architecture with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String arch;

    /**
     * Category with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String category;

    /**
     * Version with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String version;

    /**
     * Time order with an enum value from TimeOrderEnum.isValidCategory, defaulting to "desc".
     */
    @EnumValue(enumClass = TimeOrderEnum.class, enumMethod = "isValidCategory")
    private String timeOrder = "desc";

    /**
     * Page number within the range of PackageConstant.MIN_PAGE_NUM and PackageConstant.MAX_PAGE_NUM.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    /**
     * Page size within the range of PackageConstant.MIN_PAGE_SIZE and PackageConstant.MAX_PAGE_SIZE.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;

}

