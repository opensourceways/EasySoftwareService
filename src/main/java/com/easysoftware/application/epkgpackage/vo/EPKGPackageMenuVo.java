package com.easysoftware.application.epkgpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageMenuVo {
    /**
     * Name of the package.
     */
    private String name;

    /**
     * Unique identifier for the package.
     */
    private String id;

    /**
     * Version of the package.
     */
    private String version;

    /**
     * Operating system compatibility.
     */
    private String os;

    /**
     * Architecture type required by the package.
     */
    private String arch;

    /**
     * Category of the package.
     */
    private String category;

    /**
     * Update timestamp for the package.
     */
    private String epkgUpdateAt;

    /**
     * Source repository URL.
     */
    private String srcRepo;

    /**
     * Size of the package.
     */
    private String epkgSize;

    /**
     * Binary download URL.
     */
    private String binDownloadUrl;

    /**
     * Package identifier.
     */
    private String pkgId;

    /**
     * Subpath information.
     */
    private String subPath;

}
