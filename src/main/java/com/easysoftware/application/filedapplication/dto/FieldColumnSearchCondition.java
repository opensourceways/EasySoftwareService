package com.easysoftware.application.filedapplication.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldColumnSearchCondition {
    /**
     * Column (maximum length: 50).
     */
    @Size(max = 50)
    private String column;

    /**
     * Name (maximum length: 50).
     */
    @Size(max = 50)
    private String name;

}
