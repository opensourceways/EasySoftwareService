package com.easysoftware.application.applicationversion.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationVersionSearchCondition {
    /**
     * Name of the package. Restricted by length and character pattern.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_ ]+$",
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String name;

    /**
     * Page number within the allowable range.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    /**
     * Page size within the allowable range.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;

    /**
     * eulerOSVersion.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String eulerOsVersion;

    /**
     * column.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String column;

    /**
     * Name order.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String nameOrder;
}

