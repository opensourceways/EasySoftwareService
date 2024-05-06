package com.easysoftware.application.operationconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;

@Service
public class OperationConfigServiceImpl implements OperationConfigService {

    /**
     * Autowired OperationConfigGateway for handling operation configurations.
     */
    @Autowired
    private OperationConfigGateway gateway;

    /**
     * Inserts an operation configuration based on the input.
     *
     * @param input The input operation configuration to insert.
     * @return True if the operation configuration was successfully inserted, false otherwise.
     */
    @Override
    public boolean insertOperationConfig(final InputOperationConfig input) {
        // boolean
        return gateway.insertOperationConfig(input);
    }

    /**
     * Deletes operation configurations by type.
     *
     * @param type The type of operation configurations to delete.
     * @return True if the operation configurations were successfully deleted, false otherwise.
     */
    @Override
    public boolean deleteByType(final String type) {
        return gateway.deleteByType(type);
    }


}
