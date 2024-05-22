package com.easysoftware.application.applicationpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageEulerVersionVo {
    /**
     * OpenEuler version of the package.
     */
    private String os;

    /**
     * OpenEuler arch of the package.
     */
    private String arch;

    /**
     * pkgId of the package.
     */
    private String pkgId;
}
