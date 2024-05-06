package com.easysoftware.infrastructure.operationconfig.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;
import com.easysoftware.infrastructure.mapper.OperationConfigDOMapper;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.converter.OperationConfigConverter;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationConfigGatewayImpl implements OperationConfigGateway {


    /**
     * Autowired OperationConfigDOMapper for database operations.
     */
    @Autowired
    private OperationConfigDOMapper mapper;

    /**
     * Insert an operation configuration based on the input.
     *
     * @param input The input operation configuration to insert
     * @return true if the insertion was successful, false otherwise
     */
    public boolean insertOperationConfig(final InputOperationConfig input) {
        OperationConfigDO operationConfigDO = OperationConfigConverter.toDataObject(input);
        int mark = mapper.insert(operationConfigDO);
        return mark == 1;
    }

    /**
     * Delete operation configurations by type.
     *
     * @param type The type of operation configurations to delete
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    public boolean deleteByType(final String type) {
        QueryWrapper<OperationConfigDO> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        mapper.delete(wrapper);
        return true;
    }

    /**
     * Select all operation configurations and return them as a list of OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation configurations
     */
    @Override
    public List<OperationConfigVo> selectAll() {
        List<OperationConfigDO> doList = mapper.selectList(null);
        return OperationConfigConverter.toVo(doList);
    }
}
