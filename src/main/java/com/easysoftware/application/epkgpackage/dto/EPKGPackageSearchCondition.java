package com.easysoftware.application.epkgpackage.dto;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EPKGPackageSearchCondition {
    @Size(max = 50)
    @NotBlank
    private String name;

    @Range(min = 1, max = 10000, message = "page must be greater than 0 and less than 10000 ")
    private Integer pageNum = 1;

    @Range(min = 5, max = 50, message = "page must be greater than 5 and less than 50 ")
    private Integer pageSize = 10;
}
