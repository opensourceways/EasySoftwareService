package com.easysoftware.application.operationconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputOperationConfig {
    private String categorys;
    private String orderIndex;
    private String recommend;
    private String type;
}
