package com.easysoftware.application.applicationpackage.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;
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
public class ApplicationPackageSearchCondition {
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String name;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String pkgId;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String subPath;

    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String version;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String os;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String arch;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String category;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String appUpdateAt;

    @EnumValue(enumClass = TimeOrderEnum.class, enumMethod = "isValidCategory")
    private String timeOrder = "desc";
}

