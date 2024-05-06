package com.easysoftware.application.operationconfig.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationConfigVo {
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
