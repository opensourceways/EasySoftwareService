package com.easysoftware.infrastructure.operationconfig.gatewayimpl;

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
     * Select all operation configurations and return them as a list of
     * OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation
     *         configurations
     */
    @Override
    public List<OperationConfigVo> selectAll() {
        List<OperationConfigDO> doList = mapper.selectList(null);
        return OperationConfigConverter.toVo(doList);
    }
}
