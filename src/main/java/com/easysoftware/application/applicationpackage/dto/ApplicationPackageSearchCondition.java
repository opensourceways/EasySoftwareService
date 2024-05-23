package com.easysoftware.application.applicationpackage.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageSearchCondition {

    /**
     * Field name with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String name;

    /**
     * Package ID with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String pkgId;

    /**
     * Subpath with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String subPath;

    /**
     * Page number within the range of PackageConstant.MIN_PAGE_NUM to PackageConstant.
     * MAX_PAGE_NUM, default value is 1.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    @NotNull
    private Integer pageNum = 1;

    /**
     * Page size within the range of PackageConstant.MIN_PAGE_SIZE to PackageConstant.
     * MAX_PAGE_SIZE, default value is 10.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    @NotNull
    private Integer pageSize = 10;

    /**
     * Version number with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String version;

    /**
     * Operating system with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String os;

    /**
     * Architecture with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String arch;

    /**
     * Category with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String category;

    /**
     * Application update time with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String appUpdateAt;

    /**
     * Time order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String timeOrder;

    /**
     * Name order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.STR_REG,
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String nameOrder;
}

