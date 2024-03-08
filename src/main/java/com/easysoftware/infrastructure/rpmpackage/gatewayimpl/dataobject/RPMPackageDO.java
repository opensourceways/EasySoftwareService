package com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

import javax.persistence.Id;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rpm_pkg_base")
public class RPMPackageDO {
    @Serial
    private String id;

    private Timestamp createAt;

    private Timestamp updateAt;

    private String name;

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

    private String maintianerEmail;

    private String maintainerGiteeId;

    private String maintainerUpdateAt;

    private String maintainerStatus;

    private String upStream;

    private String security;

    private String similarPkgs;
}
