package com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

import javax.persistence.Id;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("rpm_pkg_base")
public class RPMPackageDO {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private Timestamp createAt;

    private Timestamp updateAt;

    private String headerEnd;

    private String sizeInstalled;

    private String timeFile;

    private String sizePackage;

    private String description;

    private String checksumPkgid;

    private String locationHref;

    private String rpmBuildhost;

    private String checksumType;

    private String sizeArchive;

    private String rpmVendor;

    private String checksum;

    private String rpmGroup;

    private String headerStart;

    private String summary;

    private String versionRel;

    private String versionVer;

    private String packager;

    private String url;

    private String versionEpoch;

    private String rpmSourcerpm;

    private String rpmLicense;

    private String name;

    private String timeBuild;

    private String arch;

    private String osName;

    private String osVer;

    private String osType;

    private String files;

    private String provides;

    private String requires;
}
