package com.easysoftware.application.operationconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;

@Service
public class OperationConfigServiceImpl implements OperationConfigService {

    @Autowired
    private OperationConfigGateway gateway;

    @Override
    public boolean insertOperationConfig(InputOperationConfig input) {
        // boolean
        return gateway.insertOperationConfig(input);
    }

    @Override
    public boolean deleteByType(String type) {
        return gateway.deleteByType(type);
    }

    
}
