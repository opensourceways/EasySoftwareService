package com.easysoftware.application.filedapplication.dto;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputFiledApplication {
    private String os;

    private String arch;

    private String name;

    private String version;

    private String category;

    private String iconUrl;

    private Set<String> tags;
    
    private Map<String,String> pkgIds;

    private String description;
}
