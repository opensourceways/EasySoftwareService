package com.easysoftware.application.applicationversion.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationVersionSearchCondition {
    @Size(max = 50)
    @NotBlank
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_ ]+$", message = "Include only letters, digits, and special characters(_-()$.)")
    private String name;

    @Range(min = 1, max = 1000, message = "page must be greater than 0 and less than 1000 ")
    private Integer pageNum = 1;

    @Range(min = 5, max = 50, message = "page must be greater than 5 and less than 50 ")
    private Integer pageSize = 10;
}

