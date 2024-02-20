package com.easysoftware.entity.dto;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import com.easysoftware.util.ValidList;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputApplicationPackage {
    @Size(max = 10000, message = "the length of description can not exceed 10000")
    public String description;
    @NotBlank(message = "name can not be null")
    @Size(max = 255, message = "the length of name can not exceed 255")
    public String name;

    @Size(max = 1000, message = "the length of arch can not exceed 5000")
    public String license;


    // app
    @Size(max = 10000, message = "the length of download can not exceed 10000")
	private String download;
    @Size(max = 10000, message = "the length of environment can not exceed 10000")
	private String environment;
    @Size(max = 10000, message = "the length of installation can not exceed 10000")
	private String installation;

    @Size(max = 10000, message = "the length of similarPkgs can not exceed 10000")
    private String similarPkgs;

    @Size(max = 10000, message = "the length of dependencyPkgs can not exceed 10000")
    private String dependencyPkgs;
}
