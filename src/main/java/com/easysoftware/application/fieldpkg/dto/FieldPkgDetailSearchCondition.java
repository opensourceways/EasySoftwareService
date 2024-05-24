package com.easysoftware.application.fieldpkg.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldPkgDetailSearchCondition {
    /**
     * RPM package ID (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String rpmPkgId;

    /**
     * EPKG package ID (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String epkgPkgId;

    /**
     * Application package ID (maximum length: PackageConstant.MAX_FIELD_LENGTH).
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String appPkgId;
}
