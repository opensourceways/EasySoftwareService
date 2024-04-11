package com.easysoftware.domain.operationconfig.gateway;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;

public interface OperationConfigGateway {
    boolean insertOperationConfig(InputOperationConfig input);
    boolean truncateTable();
}
