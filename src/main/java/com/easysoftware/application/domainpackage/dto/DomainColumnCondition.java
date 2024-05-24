package com.easysoftware.application.domainpackage.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainColumnCondition {
    /**
     * Name with a maximum size of 50 characters and must not be blank.
     */
    @Size(max = 50)
    @NotBlank
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String name;

    /**
     * Column with a maximum size of 50 characters and must not be blank.
     */
    @Size(max = 50)
    @NotBlank
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String column;

}
