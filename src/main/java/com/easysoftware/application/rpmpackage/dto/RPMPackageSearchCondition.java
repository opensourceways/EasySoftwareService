package com.easysoftware.application.rpmpackage.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.exception.enumvalid.EnumValue;
import com.easysoftware.common.exception.enumvalid.TimeOrderEnum;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageSearchCondition {
    @Size(max = 50)
    private String name;

    @Size(max = 255)
    private String pkgId;

    @Size(max = 255)
    private String subPath;

    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;

    @Size(max = 50)
    private String version;

    @Size(max = 50)
    private String os;

    @Size(max = 50)
    private String arch;

    @Size(max = 50)
    private String category;

    @Size(max = 50)
    private String rpmUpdateAt;

    @EnumValue(enumClass = TimeOrderEnum.class, enumMethod = "isValidCategory")
    private String timeOrder = "desc";
}
