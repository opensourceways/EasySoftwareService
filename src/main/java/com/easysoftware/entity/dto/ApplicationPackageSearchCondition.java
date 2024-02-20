package com.easysoftware.entity.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageSearchCondition {

    @Size(max = 50)
    private String description;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String license;

    @Size(max = 50)
	private String download;

    @Size(max = 50)
	private String environment;

    @Size(max = 50)
	private String installation;

    @Size(max = 50)
    private String similarPkgs;

    @Size(max = 50)
    private String dependencyPkgs;

    @Range(min = 1, max = 1000, message = "page must be greater than 0 and less than 1000 ")
    private Integer pageNum = 1;

    @Range(min = 5, max = 50, message = "page must be greater than 5 and less than 50 ")
    private Integer pageSize = 10;

    @Pattern(regexp = "\\b(?:exact|fuzzy)\\b", message = "exactSearch: exact / fuzzy")
    private String exactSearch = "fuzzy";
}
