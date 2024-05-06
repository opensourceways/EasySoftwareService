package com.easysoftware.domain.operationconfig.gateway;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;

import java.util.List;

public interface OperationConfigGateway {
    /**
     * Insert an operation configuration based on the input.
     *
     * @param input The input operation configuration to insert
     * @return true if the insertion was successful, false otherwise
     */
    boolean insertOperationConfig(InputOperationConfig input);

    /**
     * Delete operation configurations by type.
     *
     * @param type The type of operation configurations to delete
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteByType(String type);

    /**
     * Select all operation configurations and return them as a list of OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation configurations
     */
    List<OperationConfigVo> selectAll();

}
