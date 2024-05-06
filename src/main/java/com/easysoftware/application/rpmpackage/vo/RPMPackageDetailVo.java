package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageDetailVo {
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
     * Source code download URL for the package.
     */
    private String srcDownloadUrl;

    /**
     * Summary description of the package.
     */
    private String summary;

    /**
     * Operating system support information.
     */
    private String osSupport;

    /**
     * Repository information.
     */
    private String repo;

    /**
     * Type of repository.
     */
    private String repoType;

    /**
     * Installation instructions for the package.
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
     * Functionalities provided by the package.
     */
    private String provides;

    /**
     * Conflicting packages.
     */
    private String conflicts;

    /**
     * Changelog of the package.
     */
    private String changeLog;

    /**
     * ID of the maintainer.
     */
    private String maintainerId;

    /**
     * Email of the maintainer.
     */
    private String maintainerEmail;

    /**
     * Gitee ID of the maintainer.
     */
    private String maintainerGiteeId;

    /**
     * Timestamp of last maintainer update.
     */
    private String maintainerUpdateAt;

    /**
     * Status of the maintainer.
     */
    private String maintainerStatus;

    /**
     * Upstream source of the package.
     */
    private String upStream;

    /**
     * Security information related to the package.
     */
    private String security;

    /**
     * Similar packages available.
     */
    private String similarPkgs;

    /**
     * Download count of the package.
     */
    private String downloadCount;

    /**
     * Package ID.
     */
    private String pkgId;

    /**
     * Subpath of the package.
     */
    private String subPath;

}
