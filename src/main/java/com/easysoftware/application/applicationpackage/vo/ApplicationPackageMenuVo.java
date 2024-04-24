package com.easysoftware.application.applicationpackage.vo;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageMenuVo {
    private String name;
    private String osSupport;
    private String os;
    private String appVer;
    private String id;
    private String category;
    private String arch;
    private String type;
    private String pkgId;
    private String imageTags;
    private String download;
}
