package com.easysoftware.domain.rpmpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPMPackageUnique {
    /**
     * Name of the entity.
     */
    private String name;

    /**
     * Version of the entity.
     */
    private String version;

    /**
     * Operating system associated with the entity.
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
     * Timestamp indicating the last update for the entity.
     */
    private String rpmUpdateAt;


}
