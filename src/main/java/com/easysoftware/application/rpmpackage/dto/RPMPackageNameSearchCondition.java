package com.easysoftware.application.rpmpackage.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageNameSearchCondition {
    /**
     * Field for name with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String name;
}
