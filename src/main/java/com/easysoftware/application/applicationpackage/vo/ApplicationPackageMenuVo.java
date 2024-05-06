package com.easysoftware.application.applicationpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageMenuVo {
    /**
     * Name of the software package.
     */
    private String name;

    /**
     * Operating system support information for the software package.
     */
    private String osSupport;

    /**
     * Operating system compatibility for the software package.
     */
    private String os;

    /**
     * Application version of the software package.
     */
    private String appVer;

    /**
     * Unique identifier for the software package.
     */
    private String id;

    /**
     * Category of the software package.
     */
    private String category;

    /**
     * Architecture requirements for the software package.
     */
    private String arch;

    /**
     * Type or category of the software package.
     */
    private String type;

    /**
     * Package ID of the software package.
     */
    private String pkgId;

    /**
     * Tags associated with the software package.
     */
    private String imageTags;

    /**
     * Download details for the software package.
     */
    private String download;

}
