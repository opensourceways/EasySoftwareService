package com.easysoftware.application.externalos.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputExternalOs {
    /**
     * Name (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of name cannot exceed 255")
    private String name;

    /**
     * Identifier (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of id cannot exceed 255")
    private String id;

    /**
     * Original operating system name (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of originOsName cannot exceed 255")
    private String originOsName;

    /**
     * Original operating system version (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of originOsVer cannot exceed 255")
    private String originOsVer;

    /**
     * Original package name (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of originPkg cannot exceed 255")
    private String originPkg;

    /**
     * Target operating system name (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of targetOsName cannot exceed 255")
    private String targetOsName;

    /**
     * Target operating system version (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of targetOsVer cannot exceed 255")
    private String targetOsVer;

    /**
     * Target package name (maximum length: 255 characters).
     */
    @Size(max = 255, message = "the length of targetPkg cannot exceed 255")
    private String targetPkg;


}
