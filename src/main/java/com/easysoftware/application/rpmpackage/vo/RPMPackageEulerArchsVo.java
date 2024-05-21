package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageEulerArchsVo {
    /**
     * OpenEuler versions of the package.
     */
    private String arch;
}
