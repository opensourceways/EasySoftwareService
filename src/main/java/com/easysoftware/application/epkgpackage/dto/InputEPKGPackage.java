package com.easysoftware.application.epkgpackage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputEPKGPackage {
    @Size(max = 255, message = "the length of name can not exceed 255")
    @NotBlank
    private String name;

    @Size(max = 255, message = "the length of id can not exceed 255")
    private String id;

    @Size(max = 255, message = "the length of version can not exceed 255")
    private String version;

    @Size(max = 255, message = "the length of os can not exceed 255")
    private String os;

    @Size(max = 255, message = "the length of arch can not exceed 255")
    private String arch;

    @Size(max = 255, message = "the length of epkgCategory can not exceed 255")
    private String epkgCategory;

    @Size(max = 255, message = "the length of epkgUpdateAt can not exceed 255")
    private String epkgUpdateAt;

    @Size(max = 255, message = "the length of srcRepo can not exceed 255")
    private String srcRepo;

    @Size(max = 255, message = "the length of epkgSize can not exceed 255")
    private String epkgSize;

    @Size(max = 255, message = "the length of binDownloadUrl can not exceed 255")
    private String binDownloadUrl;

    @Size(max = 255, message = "the length of srcDownloadUrl can not exceed 255")
    private String srcDownloadUrl;

    @Size(max = 255, message = "the length of summary can not exceed 255")
    private String summary;

    @Size(max = 255, message = "the length of osSupport can not exceed 255")
    private String osSupport;

    @Size(max = 255, message = "the length of repo can not exceed 255")
    private String repo;

    @Size(max = 255, message = "the length of repoType can not exceed 255")
    private String repoType;

    @Size(max = 10000, message = "the length of installation can not exceed 10000")
    private String installation;

    @Size(max = 10000, message = "the length of description can not exceed 10000")
    private String description;

    @Size(max = 100_0000, message = "the length of requires can not exceed 100_0000")
    private String requires;
    
    @Size(max = 100_0000, message = "the length of provides can not exceed 100_0000")
    private String provides;

    @Size(max = 100_0000, message = "the length of conflicts can not exceed 100_0000")
    private String conflicts;

    @Size(max = 100_0000, message = "the length of changeLog can not exceed 100_0000")
    private String changeLog;

    @Size(max = 100_0000, message = "the length of path can not exceed 100_0000")
    private String files;

    @Size(max = 255, message = "the length of maintainerId can not exceed 255")
    private String maintainerId;

    @Size(max = 255, message = "the length of maintainerEmail can not exceed 255")
    private String maintainerEmail;

    @Size(max = 255, message = "the length of maintainerGiteeId can not exceed 255")
    private String maintainerGiteeId;

    @Size(max = 255, message = "the length of maintainerUpdateAt can not exceed 255")
    private String maintainerUpdateAt;

    @Size(max = 255, message = "the length of maintainerStatus can not exceed 255")
    private String maintainerStatus;

    @Size(max = 255, message = "the length of upStream can not exceed 255")
    private String upStream;

    @Size(max = 255, message = "the length of security can not exceed 255")
    private String security;

    @Size(max = 1000, message = "the length of similarPkgs can not exceed 1000")
    private String similarPkgs;

    @Size(max = 255, message = "the length of downloadCount can not exceed 255")
    private String downloadCount;

    @Size(max = 255, message = "the length of pkgId can not exceed 255")
    private String pkgId;
}
