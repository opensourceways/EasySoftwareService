package com.easysoftware.domain.applicationversion;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class ApplicationVersion {
    /**
     * Class representing a specific entity with various properties.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the entity.
     */
    private String name;

    /**
     * URL for the upstream homepage.
     */
    private String upHomepage;

    /**
     * URL for the EulerOS homepage.
     */
    private String eulerHomepage;

    /**
     * Backend information.
     */
    private String backend;

    /**
     * Upstream version details.
     */
    private String upstreamVersion;

    /**
     * OpenEuler version details.
     */
    private String openeulerVersion;

    /**
     * Continuous Integration (CI) version details.
     */
    private String ciVersion;

    /**
     * Status of the entity.
     */
    private String status;

    /**
     * Unique identifier of the entity.
     */
    private String id;

}
