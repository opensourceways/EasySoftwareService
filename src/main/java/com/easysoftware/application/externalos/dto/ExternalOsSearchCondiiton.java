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
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;
    
    @Size(max = 50)
    private String originOsName;

    @Size(max = 50)
    private String originOsVer;

    @Size(max = 50)
    private String originPkg;

    @Size(max = 50)
    private String targetOsName;

    @Size(max = 50)
    private String targetOsVer;

    @Size(max = 50)
    private String targetPkg;
}
