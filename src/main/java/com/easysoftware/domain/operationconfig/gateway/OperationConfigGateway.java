package com.easysoftware.domain.operationconfig.gateway;

import com.easysoftware.application.operationconfig.vo.OperationConfigVo;

import java.util.List;

public interface OperationConfigGateway {
    /**
     * Select all operation configurations and return them as a list of OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation configurations
     */
    List<OperationConfigVo> selectAll();

}
