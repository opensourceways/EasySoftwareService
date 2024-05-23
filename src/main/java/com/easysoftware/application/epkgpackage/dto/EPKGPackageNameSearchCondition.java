package com.easysoftware.application.epkgpackage.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageNameSearchCondition {
    /**
     * Name field with a maximum length defined by PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String name;

}
