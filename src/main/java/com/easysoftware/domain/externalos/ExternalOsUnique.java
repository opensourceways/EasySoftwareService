package com.easysoftware.domain.externalos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalOsUnique {
    private String originOsName;

    private String originOsVer;

    private String originPkg;

    private String targetOsName;

    private String targetOsVer;

    private String targetPkg;
}
