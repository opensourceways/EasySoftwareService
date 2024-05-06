package com.easysoftware.application.externalos.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalOsSearchCondiiton {
    /**
     * Page number within the specified range.
     */
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    /**
     * Page size within the specified range.
     */
    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;

    /**
     * Name of the original operating system (maximum 50 characters).
     */
    @Size(max = 50)
    private String originOsName;

    /**
     * Version of the original operating system (maximum 50 characters).
     */
    @Size(max = 50)
    private String originOsVer;

    /**
     * Name of the original package (maximum 50 characters).
     */
    @Size(max = 50)
    private String originPkg;

    /**
     * Name of the target operating system (maximum 50 characters).
     */
    @Size(max = 50)
    private String targetOsName;

    /**
     * Version of the target operating system (maximum 50 characters).
     */
    @Size(max = 50)
    private String targetOsVer;

    /**
     * Name of the target package (maximum 50 characters).
     */
    @Size(max = 50)
    private String targetPkg;

}
