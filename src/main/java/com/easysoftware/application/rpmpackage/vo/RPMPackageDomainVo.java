package com.easysoftware.application.rpmpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageDomainVo {
    /**
     * Category of the package.
     */
    private String category;

    /**
     * Description of the package.
     */
    private String description;

    /**
     * Name of the package.
     */
    private String name;

    /**
     * List of tags associated with the package.
     */
    private List<String> tags;

    /**
     * ID of the package.
     */
    private String id;

    /**
     * Package ID.
     */
    private String pkgId;

    /**
     * Subpath of the package.
     */
    private String subPath;

    /**
     * License.
     */
    private String license;

}
