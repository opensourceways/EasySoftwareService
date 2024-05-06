package com.easysoftware.application.rpmpackage.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputRPMPackage {
    /**
     * The length of the name should not exceed 255 characters.
     */
    @Size(max = 255, message = "the length of name can not exceed 255")
    private String name;

    /**
     * The length of the id should not exceed 255 characters.
     */
    @Size(max = 255, message = "the length of id can not exceed 255")
    private String id;

    /**
     * The length of version can not exceed 255.
     */
    @Size(max = 255, message = "the length of version can not exceed 255")
    private String version;

    /**
     * The length of os can not exceed 255.
     */
    @Size(max = 255, message = "the length of os can not exceed 255")
    private String os;

    /**
     * The length of arch can not exceed 255.
     */
    @Size(max = 255, message = "the length of arch can not exceed 255")
    private String arch;

    /**
     * The length of rpmCategory can not exceed 255.
     */
    @Size(max = 255, message = "the length of rpmCategory can not exceed 255")
    private String category;

    /**
     * The length of rpmUpdateAt can not exceed 255.
     */
    @Size(max = 255, message = "the length of rpmUpdateAt can not exceed 255")
    private String rpmUpdateAt;

    /**
     * The length of srcRepo can not exceed 255.
     */
    @Size(max = 255, message = "the length of srcRepo can not exceed 255")
    private String srcRepo;

    /**
     * The length of rpmSize can not exceed 255.
     */
    @Size(max = 255, message = "the length of rpmSize can not exceed 255")
    private String rpmSize;

    /**
     * The length of binDownloadUrl can not exceed 255.
     */
    @Size(max = 255, message = "the length of binDownloadUrl can not exceed 255")
    private String binDownloadUrl;

    /**
     * The length of srcDownloadUrl can not exceed 255.
     */
    @Size(max = 255, message = "the length of srcDownloadUrl can not exceed 255")
    private String srcDownloadUrl;

    /**
     * The length of summary can not exceed 255.
     */
    @Size(max = 255, message = "the length of summary can not exceed 255")
    private String summary;

    /**
     * The length of osSupport can not exceed 255.
     */
    @Size(max = 255, message = "the length of osSupport can not exceed 255")
    private String osSupport;

    /**
     * The length of repo can not exceed 255.
     */
    @Size(max = 255, message = "the length of repo can not exceed 255")
    private String repo;

    /**
     * The length of repoType can not exceed 255.
     */
    @Size(max = 255, message = "the length of repoType can not exceed 255")
    private String repoType;

    /**
     * The length of installation can not exceed 10000.
     */
    @Size(max = 10000, message = "the length of installation can not exceed 10000")
    private String installation;

    /**
     * The length of description can not exceed 10000.
     */
    @Size(max = 10000, message = "the length of description can not exceed 10000")
    private String description;
    /**
     * The length of requires can not exceed 100_0000.
     */
    @Size(max = 100_0000, message = "the length of requires can not exceed 100_0000")
    private String requires;

    /**
     * The length of provides can not exceed 100_0000.
     */
    @Size(max = 100_0000, message = "the length of provides can not exceed 100_0000")
    private String provides;

    /**
     * The length of conflicts can not exceed 100_0000.
     */
    @Size(max = 100_0000, message = "the length of conflicts can not exceed 100_0000")
    private String conflicts;

    /**
     * The length of changeLog can not exceed 100_0000.
     */
    @Size(max = 100_0000, message = "the length of changeLog can not exceed 100_0000")
    private String changeLog;

    /**
     * The length of maintainerId can not exceed 255.
     */
    @Size(max = 255, message = "the length of maintainerId can not exceed 255")
    private String maintainerId;

    /**
     * The length of maintainerEmail can not exceed 255.
     */
    @Size(max = 255, message = "the length of maintainerEmail can not exceed 255")
    private String maintainerEmail;

    /**
     * The length of maintainerGiteeId can not exceed 255.
     */
    @Size(max = 255, message = "the length of maintainerGiteeId can not exceed 255")
    private String maintainerGiteeId;

    /**
     * The length of maintainerUpdateAt can not exceed 255.
     */
    @Size(max = 255, message = "the length of maintainerUpdateAt can not exceed 255")
    private String maintainerUpdateAt;

    /**
     * The length of maintainerStatus can not exceed 255.
     */
    @Size(max = 255, message = "the length of maintainerStatus can not exceed 255")
    private String maintainerStatus;

    /**
     * The length of upStream can not exceed 255.
     */
    @Size(max = 255, message = "the length of upStream can not exceed 255")
    private String upStream;

    /**
     * The length of security can not exceed 255.
     */
    @Size(max = 255, message = "the length of security can not exceed 255")
    private String security;

    /**
     * The length of similarPkgs can not exceed 1000.
     */
    @Size(max = 1000, message = "the length of similarPkgs can not exceed 1000")
    private String similarPkgs;

    /**
     * The length of downloadCount can not exceed 100.
     */
    @Size(max = 100, message = "the length of downloadCount can not exceed 100")
    private String downloadCount;

    /**
     * The length of pkgId can not exceed 255.
     */
    @Size(max = 255, message = "the length of pkgId can not exceed 255")
    private String pkgId;

    /**
     * The length of subPath can not exceed 255.
     */
    @Size(max = 255, message = "the length of subPath can not exceed 255")
    private String subPath;

}
