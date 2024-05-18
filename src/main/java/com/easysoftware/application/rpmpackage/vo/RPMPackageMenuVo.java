package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageMenuVo {
    /**
     * Name of the RPM package.
     */
    private String name;

    /**
     * ID of the RPM package.
     */
    private String id;

    /**
     * Version of the RPM package.
     */
    private String version;

    /**
     * Operating system for which the package is intended.
     */
    private String os;

    /**
     * Architecture of the package.
     */
    private String arch;

    /**
     * Category to which the package belongs.
     */
    private String category;

    /**
     * Timestamp of the last RPM update.
     */
    private String rpmUpdateAt;

    /**
     * Source repository for the package.
     */
    private String srcRepo;

    /**
     * Size of the RPM package.
     */
    private String rpmSize;

    /**
     * Binary download URL for the package.
     */
    private String binDownloadUrl;

    /**
     * Package ID.
     */
    private String pkgId;

    /**
     * Subpath of the package.
     */
    private String subPath;

    /**
     * License.
     */
    private String license;

}
