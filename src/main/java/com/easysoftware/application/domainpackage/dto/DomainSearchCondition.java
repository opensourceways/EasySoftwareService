package com.easysoftware.application.domainpackage.dto;

import org.hibernate.validator.constraints.Range;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.exception.enumvalid.DomainNameEnum;
import com.easysoftware.common.exception.enumvalid.EnumValue;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String entity;

    /**
     * Operating system with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String os;

    /**
     * Architecture with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String arch;

    /**
     * Category with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String category;

    /**
     * Version with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String version;

    /**
     * Time order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String timeOrder;

    /**
     * Page number within the range of PackageConstant.MIN_PAGE_NUM and PackageConstant.MAX_PAGE_NUM.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    @NotNull
    private Integer pageNum = 1;

    /**
     * Page size within the range of PackageConstant.MIN_PAGE_SIZE and PackageConstant.MAX_PAGE_SIZE.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    @NotNull
    private Integer pageSize = 10;

    /**
     * Name order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String nameOrder;
}

