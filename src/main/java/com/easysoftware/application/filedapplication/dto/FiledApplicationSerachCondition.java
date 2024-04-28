package com.easysoftware.application.filedapplication.dto;

import com.easysoftware.common.constant.PackageConstant;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiledApplicationSerachCondition {
    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String os;

    @Size(max = PackageConstant.MAX_FIELD_LENGTH)
    private String arch;
}
