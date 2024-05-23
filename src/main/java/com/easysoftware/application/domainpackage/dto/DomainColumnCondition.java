package com.easysoftware.application.domainpackage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainColumnCondition {
    /**
     * Name with a maximum size of 50 characters and must not be blank.
     */
    @Size(max = 50)
    @NotBlank
    private String name;

    /**
     * Column with a maximum size of 50 characters and must not be blank.
     */
    @Size(max = 50)
    @NotBlank
    private String column;

}
