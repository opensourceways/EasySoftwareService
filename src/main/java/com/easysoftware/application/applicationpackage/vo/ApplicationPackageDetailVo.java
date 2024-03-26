package com.easysoftware.application.applicationpackage.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageDetailVo {
    private String description;

    private String id;

    private String name;

    private String license;

    private String download;

    private String environment;

    private String installation;

    private String similarPkgs;

    private String category;

    private String dependencyPkgs;

    private String appVer;

    private String osSupport;

    private String os;

    private String arch;

    private String maintainerId;

    private String maintainerEmail;

    private String maintainerGiteeId;

    private String maintainerUpdateAt;

    private String securityLevel;

    private String safeLabel;

    private String downloadCount;

    private String appSize;

    private String srcRepo;

    private String srcDownloadUrl;

    private String binDownloadUrl;

    private String type;

    private String iconUrl;

    private String pkgId;
}
