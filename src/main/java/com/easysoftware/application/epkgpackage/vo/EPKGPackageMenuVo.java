package com.easysoftware.application.epkgpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageMenuVo {
    private String name;
    private String id;
    private String version;
    private String os;
    private String arch;
    private String epkgCategory;
    private String epkgUpdateAt;
    private String srcRepo;
    private String epkgSize;
    private String binDownloadUrl;
    private String pkgId;
}
