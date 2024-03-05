package com.easysoftware.application.epkgpackage.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageSearchCondition {
    @Size(max = 50)
    @NotBlank
    private String name;

    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;
}
