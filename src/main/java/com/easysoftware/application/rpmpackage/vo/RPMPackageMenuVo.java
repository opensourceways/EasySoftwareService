package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageMenuVo {
    private String name;
    private String version;
    private String os;
    private String arch;
    private String rpmCategory;
    private String rpmUpdateAt;
    private String srcRepo;
    private String rpmSize;
    private String binDownloadUrl;
}