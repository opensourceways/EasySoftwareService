package com.easysoftware.application.operationconfig;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;

public interface OperationConfigService {
    /**
     * Inserts an operation configuration based on the input.
     *
     * @param input The input operation configuration to insert.
     * @return True if the operation configuration was successfully inserted, false otherwise.
     */
    boolean insertOperationConfig(InputOperationConfig input);

    /**
     * Deletes operation configurations by type.
     *
     * @param type The type of operation configurations to delete.
     * @return True if the operation configurations were successfully deleted, false otherwise.
     */
    boolean deleteByType(String type);

}
