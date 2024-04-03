package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageMenuVo {
    private String name;
    private String id;
    private String version;
    private String os;
    private String arch;
    private String category;
    private String rpmUpdateAt;
    private String srcRepo;
    private String rpmSize;
    private String binDownloadUrl;
    private String pkgId;
    private String subPath;
}
