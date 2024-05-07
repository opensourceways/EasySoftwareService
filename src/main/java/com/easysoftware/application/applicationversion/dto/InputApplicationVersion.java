package com.easysoftware.application.applicationversion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputApplicationVersion {
    /**
     * Name of the software. Restricted by length and character pattern.
     */
    @NotBlank(message = "name can not be null")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_:/ ]+$",
            message = "Include only letters, digits, and special characters(_-()$.)")
    @Size(max = 45, message = "the length of name can not exceed 45")
    private String name;

    /**
     * Homepage URL with length restriction and character pattern.
     */
    @Size(max = 255, message = "the length of homepage can not exceed 255")
    @Pattern(regexp = "^[A-Za-z0-9.()\\-_:/ ]+$",
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String eulerHomepage;

    /**
     * Upstream homepage URL with length restriction and character pattern.
     */
    @Size(max = 255, message = "the length of homepage can not exceed 255")
    @Pattern(regexp = "^[A-Za-z0-9.()\\-_:/ ]+$",
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String upHomepage;

    /**
     * Backend information with length restriction and character pattern.
     */
    @Size(max = 45, message = "the length of backend can not exceed 45")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_:/ ]+$",
            message = "Include only letters, digits, and special characters(_-()$.)")
    private String backend;

    /**
     * Status information with length restriction.
     */
    @Size(max = 45, message = "the length of status can not exceed 45")
    private String status;

    /**
     * Upstream version with length restriction.
     */
    @Size(max = 45, message = "the length of upstreamVersion can not exceed 45")
    private String upstreamVersion;

    /**
     * OpenEuler version with length restriction.
     */
    @Size(max = 45, message = "the length of compatibleVersion can not exceed 45")
    private String openeulerVersion;

    /**
     * Continuous Integration (CI) version with length restriction.
     */
    @Size(max = 45, message = "the length of compatibleVersion can not exceed 45")
    private String ciVersion;

}
