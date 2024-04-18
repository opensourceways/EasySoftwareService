package com.easysoftware.domain.operationconfig.gateway;

import java.util.List;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;

public interface OperationConfigGateway {
    boolean insertOperationConfig(InputOperationConfig input);
    boolean deleteByType(String type);
    List<OperationConfigVo> selectAll();
}
