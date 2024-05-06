package com.easysoftware.application.filedapplication.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiledApplicationVo {
    /**
     * Operating system.
     */
    private String os;

    /**
     * Architecture.
     */
    private String arch;

    /**
     * Name.
     */
    private String name;

    /**
     * Version.
     */
    private String version;

    /**
     * Category.
     */
    private String category;

    /**
     * Icon URL.
     */
    private String iconUrl;

    /**
     * Set of tags.
     */
    private Set<String> tags;

    /**
     * Map of package IDs.
     */
    private Map<String, String> pkgIds;

    /**
     * Description.
     */
    private String description;

}
