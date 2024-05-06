package com.easysoftware.application.epkgpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageDetailVo {
    /**
     * Name of the package.
     */
    private String name;

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
     * Source code download URL.
     */
    private String srcDownloadUrl;

    /**
     * Brief summary of the package.
     */
    private String summary;

    /**
     * Supported operating systems.
     */
    private String osSupport;

    /**
     * Repository URL.
     */
    private String repo;

    /**
     * Type of repository.
     */
    private String repoType;

    /**
     * Installation instructions.
     */
    private String installation;

    /**
     * Detailed description of the package.
     */
    private String description;

    /**
     * Dependencies required by the package.
     */
    private String requires;

    /**
     * Features provided by the package.
     */
    private String provides;

    /**
     * Conflicting packages.
     */
    private String conflicts;

    /**
     * Changelog for the package.
     */
    private String changeLog;

    /**
     * Path to package files.
     */
    private String files;

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
     * Update timestamp for the maintainer.
     */
    private String maintainerUpdateAt;

    /**
     * Status of the maintainer.
     */
    private String maintainerStatus;

    /**
     * Upstream source information.
     */
    private String upStream;

    /**
     * Security-related information.
     */
    private String security;

    /**
     * List of similar packages.
     */
    private String similarPkgs;

    /**
     * Download count for the package.
     */
    private String downloadCount;

    /**
     * Unique identifier for the package.
     */
    private String pkgId;

    /**
     * Subpath information.
     */
    private String subPath;

}
