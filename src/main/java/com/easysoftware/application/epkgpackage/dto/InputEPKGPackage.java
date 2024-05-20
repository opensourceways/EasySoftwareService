package com.easysoftware.application.epkgpackage.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputEPKGPackage {
    /**
     * Name of the package.
     */
    @Size(max = 255, message = "the length of name cannot exceed 255")
    private String name;

    /**
     * Unique identifier for the package.
     */
    @Size(max = 255, message = "the length of id cannot exceed 255")
    private String id;

    /**
     * Version of the package.
     */
    @Size(max = 255, message = "the length of version cannot exceed 255")
    private String version;

    /**
     * Operating system compatibility.
     */
    @Size(max = 255, message = "the length of os cannot exceed 255")
    private String os;

    /**
     * Architecture type required by the package.
     */
    @Size(max = 255, message = "the length of arch cannot exceed 255")
    private String arch;

    /**
     * Category of the package.
     */
    @Size(max = 255, message = "the length of category cannot exceed 255")
    private String category;

    /**
     * Update timestamp for the package.
     */
    @Size(max = 255, message = "the length of epkgUpdateAt cannot exceed 255")
    private String epkgUpdateAt;

    /**
     * Source repository URL.
     */
    @Size(max = 255, message = "the length of srcRepo cannot exceed 255")
    private String srcRepo;

    /**
     * Size of the package.
     */
    @Size(max = 255, message = "the length of epkgSize cannot exceed 255")
    private String epkgSize;

    /**
     * Binary download URL.
     */
    @Size(max = 255, message = "the length of binDownloadUrl cannot exceed 255")
    private String binDownloadUrl;

    /**
     * Source code download URL.
     */
    @Size(max = 255, message = "the length of srcDownloadUrl cannot exceed 255")
    private String srcDownloadUrl;

    /**
     * Brief summary of the package.
     */
    @Size(max = 255, message = "the length of summary cannot exceed 255")
    private String summary;

    /**
     * Supported operating systems.
     */
    @Size(max = 255, message = "the length of osSupport cannot exceed 255")
    private String osSupport;

    /**
     * Repository URL.
     */
    @Size(max = 255, message = "the length of repo cannot exceed 255")
    private String repo;

    /**
     * Type of repository.
     */
    @Size(max = 255, message = "the length of repoType cannot exceed 255")
    private String repoType;

    /**
     * Installation instructions.
     */
    @Size(max = 10000, message = "the length of installation cannot exceed 10000")
    private String installation;

    /**
     * Detailed description of the package.
     */
    @Size(max = 10000, message = "the length of description cannot exceed 10000")
    private String description;

    /**
     * Dependencies required by the package.
     */
    @Size(max = 100_0000, message = "the length of requires can not exceed 100_0000")
    private String requires;

    /**
     * Features provided by the package.
     */
    @Size(max = 100_0000, message = "the length of provides can not exceed 100_0000")
    private String provides;

    /**
     * Conflicting packages.
     */
    @Size(max = 100_0000, message = "the length of conflicts can not exceed 100_0000")
    private String conflicts;

    /**
     * Changelog for the package.
     */
    @Size(max = 100_0000, message = "the length of changeLog can not exceed 100_0000")
    private String changeLog;

    /**
     * Path to package files.
     */
    @Size(max = 100_0000, message = "the length of path can not exceed 100_0000")
    private String files;

    /**
     * Maintainer's unique identifier.
     */
    @Size(max = 255, message = "the length of maintainerId cannot exceed 255")
    private String maintainerId;

    /**
     * Maintainer's email address.
     */
    @Size(max = 255, message = "the length of maintainerEmail cannot exceed 255")
    private String maintainerEmail;

    /**
     * Maintainer's Gitee ID.
     */
    @Size(max = 255, message = "the length of maintainerGiteeId cannot exceed 255")
    private String maintainerGiteeId;

    /**
     * Update timestamp for the maintainer.
     */
    @Size(max = 255, message = "the length of maintainerUpdateAt cannot exceed 255")
    private String maintainerUpdateAt;

    /**
     * Status of the maintainer.
     */
    @Size(max = 255, message = "the length of maintainerStatus cannot exceed 255")
    private String maintainerStatus;

    /**
     * Upstream source information.
     */
    @Size(max = 255, message = "the length of upStream cannot exceed 255")
    private String upStream;

    /**
     * Security-related information.
     */
    @Size(max = 255, message = "the length of security cannot exceed 255")
    private String security;

    /**
     * List of similar packages.
     */
    @Size(max = 1000, message = "the length of similarPkgs cannot exceed 1000")
    private String similarPkgs;

    /**
     * Download count for the package.
     */
    @Size(max = 255, message = "the length of downloadCount cannot exceed 255")
    private String downloadCount;

    /**
     * Unique identifier for the package.
     */
    @Size(max = 255, message = "the length of pkgId cannot exceed 255")
    private String pkgId;

    /**
     * Subpath information.
     */
    @Size(max = 255, message = "the length of subPath cannot exceed 255")
    private String subPath;

    /**
     * License.
     */
    @Size(max = 10_000, message = "the length of license cannot exceed 10000")
    private String license;

}
