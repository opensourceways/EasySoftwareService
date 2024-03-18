package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageDetailVo {
    private String name;

    private String id;

    private String version;

    private String os;

    private String arch;

    private String rpmCategory;

    private String rpmUpdateAt;

    private String srcRepo;

    private String rpmSize;

    private String binDownloadUrl;

    private String srcDownloadUrl;

    private String summary;

    private String osSupport;

    private String repo;

    private String repoType;

    private String installation;

    private String description;

    private String requires;
    
    private String provides;

    private String conflicts;

    private String changeLog;

    private String maintainerId;

    private String maintainerEmail;

    private String maintainerGiteeId;

    private String maintainerUpdateAt;

    private String maintainerStatus;

    private String upStream;

    private String security;

    private String similarPkgs;

    private String downloadCount;

    private String pkgId;
}
