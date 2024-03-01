package com.easysoftware.application.epkgpackage.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageDetailVo {
    private String name;

    private String version;

    private String os;

    private String arch;

    private String epkgCategory;

    private String epkgUpdateAt;

    private String srcRepo;

    private String epkgSize;

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

    private String path;

    private String maintanierId;

    private String maintianerEmail;

    private String maintainerGiteeId;

    private String maintainerUpdateAt;

    private String maintainerStatus;

    private String upStream;

    private String security;

    private String similarPkgs;
}
