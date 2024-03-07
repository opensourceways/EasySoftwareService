package com.easysoftware.application.externalos.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputExternalOs {
    @Size(max = 255, message = "the length of name can not exceed 255")
    private String name;

    @Size(max = 255, message = "the length of id can not exceed 255")
    private String id;

    @Size(max = 255, message = "the length of originOsName can not exceed 255")
    private String originOsName;

    @Size(max = 255, message = "the length of originOsVer can not exceed 255")
    private String originOsVer;

    @Size(max = 255, message = "the length of originPkg can not exceed 255")
    private String originPkg;

    @Size(max = 255, message = "the length of targetOsName can not exceed 255")
    private String targetOsName;

    @Size(max = 255, message = "the length of targetOsVer can not exceed 255")
    private String targetOsVer;

    @Size(max = 255, message = "the length of targetPkg can not exceed 255")
    private String targetPkg;

}
