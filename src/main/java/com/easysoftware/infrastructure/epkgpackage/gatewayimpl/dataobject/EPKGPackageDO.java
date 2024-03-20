package com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

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
    
    private String id;

    private Timestamp updateAt;

    private Timestamp createAt;

    private String name;

    private String version;

    private String os;

    private String arch;

    private String category;

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

    private String maintainerId;

    private String maintainerEmail;

    private String maintainerGiteeId;

    private String maintainerUpdateAt;

    private String maintainerStatus;

    private String upStream;

    private String security;

    private String similarPkgs;

    private String files;

    private String downloadCount;

    private String pkgId;
}
