package com.easysoftware.domain.epkgpackage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EPKGPackageUnique {
    private String name;
    private String version;
    private String os;
    private String arch;
    private String epkgCategory;
    private String epkgUpdateAt;
    
}
