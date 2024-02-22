package com.easysoftware.domain.applicationpackage;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationPackage {
    @Serial
    private static final long serialVersionUID = 1L;

    public String description;

    public String name;

    public String license;


    private String download;

    private String environment;
    

    private String installation;


    private String similarPkgs;

    private String appCategory;

    private String dependencyPkgs;
}
