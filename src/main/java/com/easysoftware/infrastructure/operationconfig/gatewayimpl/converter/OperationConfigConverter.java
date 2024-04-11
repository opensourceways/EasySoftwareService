package com.easysoftware.infrastructure.operationconfig.gatewayimpl.converter;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;

public class OperationConfigConverter {
    public static OperationConfigDO toDataObject(InputOperationConfig input) {
        OperationConfigDO operationConfigDO = new OperationConfigDO();
        BeanUtils.copyProperties(input, operationConfigDO);
        return operationConfigDO;
    }
}
