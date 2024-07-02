package com.easysoftware.application.oepackage.vo;

import com.easysoftware.application.applicationpackage.vo.EulerVer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OepkgEulerVersionVo implements EulerVer {
    /**
     * OpenEuler versions of the package.
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
