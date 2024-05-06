package com.easysoftware.application.applicationpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageDetailVo {

    /**
     * Description of the software package.
     */
    private String description;

    /**
     * Unique identifier for the software package.
     */
    private String id;

    /**
     * Name of the software package.
     */
    private String name;

    /**
     * License information for the software package.
     */
    private String license;

    /**
     * Download details for the software package.
     */
    private String download;

    /**
     * Environment requirements for the software package.
     */
    private String environment;

    /**
     * Installation instructions for the software package.
     */
    private String installation;

    /**
     * Similar packages related to the software package.
     */
    private String similarPkgs;

    /**
     * Category of the software package.
     */
    private String category;
    /**
     * Dependency packages required by the software package.
     */
    private String dependencyPkgs;
    /**
     * Application version of the software package.
     */
    private String appVer;
    /**
     * Operating system support information for the software package.
     */
    private String osSupport;
    /**
     * Operating system compatibility for the software package.
     */
    private String os;
    /**
     * Architecture requirements for the software package.
     */
    private String arch;
    /**
     * Maintainer's unique identifier.
     */
    private String maintainerId;
    /**
     * Maintainer's email address.
     */
    private String maintainerEmail;
    /**
     * Maintainer's Gitee ID.
     */
    private String maintainerGiteeId;
    /**
     * Last update timestamp of the maintainer.
     */
    private String maintainerUpdateAt;
    /**
     * Security level associated with the software package.
     */
    private String securityLevel;
    /**
     * Safe label information for the software package.
     */
    private String safeLabel;
    /**
     * Number of downloads for the software package.
     */
    private String downloadCount;
    /**
     * Size of the software package.
     */
    private String appSize;
    /**
     * Source repository of the software package.
     */
    private String srcRepo;
    /**
     * URL for downloading source code.
     */
    private String srcDownloadUrl;
    /**
     * URL for downloading binary files.
     */
    private String binDownloadUrl;
    /**
     * Type or category of the software package.
     */
    private String type;
    /**
     * URL for the icon of the software package.
     */
    private String iconUrl;
    /**
     * Package ID of the software package.
     */
    private String pkgId;
    /**
     * Tags associated with the software package.
     */
    private String imageTags;
    /**
     * Usage information for the software package.
     */
    private String imageUsage;
}
