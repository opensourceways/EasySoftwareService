package com.easysoftware.domain.rpmpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPMPackage {
    /**
     * Class representing an RPM package entity.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the RPM package.
     */
    private String name;

    /**
     * Version of the RPM package.
     */
    private String version;

    /**
     * Operating system for which the RPM package is intended.
     */
    private String os;

    /**
     * Architecture of the RPM package.
     */
    private String arch;

    /**
     * Category to which the RPM package belongs.
     */
    private String category;

    /**
     * Timestamp indicating when the RPM package was last updated.
     */
    private String rpmUpdateAt;

    /**
     * Source repository of the RPM package.
     */
    private String srcRepo;

    /**
     * Size of the RPM package.
     */
    private String rpmSize;

    /**
     * Binary download URL of the RPM package.
     */
    private String binDownloadUrl;

    /**
     * Source code download URL of the RPM package.
     */
    private String srcDownloadUrl;

    /**
     * Summary of the RPM package.
     */
    private String summary;

    /**
     * Operating systems supported by the RPM package.
     */
    private String osSupport;

    /**
     * Repository of the RPM package.
     */
    private String repo;

    /**
     * Type of repository for the RPM package.
     */
    private String repoType;

    /**
     * Installation information of the RPM package.
     */
    private String installation;

    /**
     * Description of the RPM package.
     */
    private String description;

    /**
     * Required dependencies for the RPM package.
     */
    private String requires;

    /**
     * Features provided by the RPM package.
     */
    private String provides;

    /**
     * Conflicts with other packages.
     */
    private String conflicts;

    /**
     * Change log for the RPM package.
     */
    private String changeLog;

    /**
     * Identifier of the maintainer.
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
     * Timestamp indicating when the maintainer was last updated.
     */
    private String maintainerUpdateAt;

    /**
     * Status of the maintainer.
     */
    private String maintainerStatus;

    /**
     * Upstream source of the RPM package.
     */
    private String upStream;

    /**
     * Security information related to the RPM package.
     */
    private String security;

    /**
     * Similar packages related to the RPM package.
     */
    private String similarPkgs;

    /**
     * Download count of the RPM package.
     */
    private String downloadCount;

    /**
     * Package ID of the RPM package.
     */
    private String pkgId;

    /**
     * Subpath of the RPM package.
     */
    private String subPath;

}
