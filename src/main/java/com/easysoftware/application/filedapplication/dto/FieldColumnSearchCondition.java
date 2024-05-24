package com.easysoftware.application.filedapplication.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldColumnSearchCondition {
    /**
     * Column (maximum length: 50).
     */
    @Size(max = 50)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String column;

    /**
     * Name (maximum length: 50).
     */
    @Size(max = 50)
    @Pattern(regexp = PackageConstant.VALID_STR_REG, message = PackageConstant.VALID_MESSAGE)
    private String name;

}
