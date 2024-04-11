package com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("operation_config")
public class OperationConfigDO {
    @Serial
    private static final long serialVersionUID = 1L;

    private String categorys;
    private String orderIndex;
    private String recommend;
    private String type;
}
