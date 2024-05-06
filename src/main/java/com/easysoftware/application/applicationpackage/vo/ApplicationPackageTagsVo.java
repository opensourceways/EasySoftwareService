package com.easysoftware.application.applicationpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageTagsVo {
    /**
     * Application version.
     */
    private String appVer;

    /**
     * Architecture information.
     */
    private String arch;

    /**
     * Docker configuration string.
     */
    private String dockerStr;

}
