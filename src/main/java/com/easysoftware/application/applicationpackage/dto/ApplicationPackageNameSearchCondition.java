package com.easysoftware.application.applicationpackage.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    @NotNull
    private String name;

}
