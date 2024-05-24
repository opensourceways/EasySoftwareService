package com.easysoftware.application.fieldpkg.vo;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldPkgVo {
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
     * List of tags.
     */
    private List<String> tags;

    /**
     * Map of package IDs.
     */
    private Map<String, String> pkgIds;

    /**
     * Description.
     */
    private String description;
}
