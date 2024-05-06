package com.easysoftware.domain.applicationpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPackage {
    /**
     * Class representing a specific entity with various properties.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Description of the entity.
     */
    private String description;

    /**
     * Name of the entity.
     */
    private String name;

    /**
     * License information.
     */
    private String license;

    /**
     * Download information.
     */
    private String download;

    /**
     * Environment details.
     */
    private String environment;

    /**
     * Installation details.
     */
    private String installation;

    /**
     * Similar packages related to the entity.
     */
    private String similarPkgs;

    /**
     * Category information.
     */
    private String category;

    /**
     * Dependency packages required.
     */
    private String dependencyPkgs;

    /**
     * URL for the icon.
     */
    private String iconUrl;

    /**
     * Application version.
     */
    private String appVer;

    /**
     * Operating system support.
     */
    private String osSupport;

    /**
     * Operating system details.
     */
    private String os;

    /**
     * Architecture details.
     */
    private String arch;

    /**
     * Maintainer ID.
     */
    private String maintainerId;

    /**
     * Maintainer's email.
     */
    private String maintainerEmail;

    /**
     * Maintainer's Gitee ID.
     */
    private String maintainerGiteeId;

    /**
     * Last update timestamp for maintainer.
     */
    private String maintainerUpdateAt;

    /**
     * Security level information.
     */
    private String securityLevel;

    /**
     * Safety label details.
     */
    private String safeLabel;

    /**
     * Download count information.
     */
    private String downloadCount;

    /**
     * Application size.
     */
    private String appSize;

    /**
     * Source repository information.
     */
    private String srcRepo;

    /**
     * Source code download URL.
     */
    private String srcDownloadUrl;

    /**
     * Binary download URL.
     */
    private String binDownloadUrl;

    /**
     * Type of the entity.
     */
    private String type;

    /**
     * Package ID.
     */
    private String pkgId;

    /**
     * Image tags associated.
     */
    private String imageTags;

    /**
     * Image usage details.
     */
    private String imageUsage;

}
