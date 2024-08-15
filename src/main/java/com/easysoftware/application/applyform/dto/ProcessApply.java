package com.easysoftware.application.applyform.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessApply {
    /**
     * Field applyStatus with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    @NotNull
    private String applyStatus;

    /**
     * apply ID.
     */
    @NotNull
    private long applyId;

    /**
     * Field comment with a maximum length of PackageConstant.MAX_FIELD_LENGTH.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    @NotNull
    private String comment;
}
