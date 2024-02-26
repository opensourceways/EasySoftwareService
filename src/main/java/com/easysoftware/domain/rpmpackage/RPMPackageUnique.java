package com.easysoftware.domain.rpmpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPMPackageUnique {
    private String name;
    private String versionVer;
    private String versionRel;
    private String osName;
    private String osVer;
    private String arch;
    private String rpmCategory;
    private String timeFile;
    
}
