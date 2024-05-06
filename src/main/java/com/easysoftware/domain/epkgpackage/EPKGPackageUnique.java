package com.easysoftware.domain.epkgpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EPKGPackageUnique {
    /**
     * Name of the entity.
     */
    private String name;

    /**
     * Version of the entity.
     */
    private String version;

    /**
     * Operating system for which the entity is intended.
     */
    private String os;

    /**
     * Architecture of the entity.
     */
    private String arch;

    /**
     * Category to which the entity belongs.
     */
    private String category;

    /**
     * Timestamp indicating when the entity was last updated in the EPKG system.
     */
    private String epkgUpdateAt;


}
