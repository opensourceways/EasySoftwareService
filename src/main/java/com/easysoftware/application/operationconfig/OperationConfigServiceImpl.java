package com.easysoftware.application.operationconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;

@Service
public class OperationConfigServiceImpl implements OperationConfigService {

    /**
     * Autowired OperationConfigGateway for handling operation configurations.
     */
    @Autowired
    private OperationConfigGateway gateway;
}
