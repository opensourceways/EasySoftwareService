package com.easysoftware.domain.externalos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalOsUnique {
    /**
     * Name of the originating operating system.
     */
    private String originOsName;

    /**
     * Version of the originating operating system.
     */
    private String originOsVer;

    /**
     * Name of the package in the originating operating system.
     */
    private String originPkg;

    /**
     * Name of the target operating system.
     */
    private String targetOsName;

    /**
     * Version of the target operating system.
     */
    private String targetOsVer;

    /**
     * Name of the package in the target operating system.
     */
    private String targetPkg;

}
