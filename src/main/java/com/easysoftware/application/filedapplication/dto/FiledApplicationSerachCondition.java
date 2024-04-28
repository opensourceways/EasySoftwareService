package com.easysoftware.application.filedapplication.dto;

import org.hibernate.validator.constraints.Range;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiledApplicationSerachCondition {
    @Range(min = PackageConstant.MIN_PAGE_NUM, max = PackageConstant.MAX_PAGE_NUM)
    private Integer pageNum = 1;

    @Range(min = PackageConstant.MIN_PAGE_SIZE, max = PackageConstant.MAX_PAGE_SIZE)
    private Integer pageSize = 10;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String os;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String arch;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String category;
}
