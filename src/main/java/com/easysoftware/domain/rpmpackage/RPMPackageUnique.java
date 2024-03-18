package com.easysoftware.domain.rpmpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPMPackageUnique {
    private String name;
    private String version;
    private String os;
    private String arch;
    private String category;
    private String rpmUpdateAt;
    
}
