package com.easysoftware.application.applicationversion.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationColumnSearchCondition {
    /**
     * column.
     */
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String column;
}
