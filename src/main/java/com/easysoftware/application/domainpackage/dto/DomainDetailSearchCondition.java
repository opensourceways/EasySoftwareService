package com.easysoftware.application.domainpackage.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainDetailSearchCondition {
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String rpmPkgId;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String epkgPkgId;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String appPkgId;
}
