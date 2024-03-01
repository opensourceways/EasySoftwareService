package com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("epkg_pkg")
public class EPKGPackageDO {
    @Serial
    private static final long serialVersionUID = 1L;
    
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

    private String maintanierId;

    private String maintianerEmail;

    private String maintainerGiteeId;

    private String maintainerUpdateAt;

    private String maintainerStatus;

    private String upStream;

    private String security;

    private String similarPkgs;
}