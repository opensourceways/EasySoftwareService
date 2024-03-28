package com.easysoftware.application.domainpackage.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainDetailSearchCondition {
    @Size(max = 200)
    private String rpmPkgId;

    @Size(max = 200)
    private String epkgPkgId;

    @Size(max = 200)
    private String appPkgId;
}
