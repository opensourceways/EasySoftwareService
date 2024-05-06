package com.easysoftware.application.operationconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputOperationConfig {
    /**
     * Categories.
     */
    private String categorys;

    /**
     * Order index.
     */
    private String orderIndex;

    /**
     * Recommendation status.
     */
    private String recommend;

    /**
     * Type.
     */
    private String type;

}
