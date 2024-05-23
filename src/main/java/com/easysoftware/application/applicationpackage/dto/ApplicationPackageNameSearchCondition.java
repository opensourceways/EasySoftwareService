package com.easysoftware.application.applicationpackage.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageNameSearchCondition {
    /**
     * Field name with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @NotNull
    private String name;

}
