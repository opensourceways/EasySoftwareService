package com.easysoftware.application.domainpackage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class DomainPackageMenuVo {
    /**
     * Category of the package.
     */
    private String category;

    /**
     * Name of the package.
     */
    private String name;

    /**
     * Description of the package.
     */
    private String description;

    /**
     * URL to the icon of the package.
     */
    private String iconUrl;

    /**
     * Set of tags associated with the package.
     */
    private Set<String> tags;

    /**
     * Map of package IDs where the key is the ID and the value is additional information.
     */
    private Map<String, String> pkgIds;


    /**
     * Constructor for DomainPackageMenuVo class.
     * Initializes tags as a new HashSet and pkgIds as a new HashMap with default values.
     */
    public DomainPackageMenuVo() {
        this.tags = new HashSet<>();
        this.pkgIds = new HashMap<>();
        this.pkgIds.put("RPM", "");
        this.pkgIds.put("EPKG", "");
        this.pkgIds.put("IMAGE", "");
    }
}