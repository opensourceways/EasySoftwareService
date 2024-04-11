package com.easysoftware.infrastructure.operationconfig.gatewayimpl;

import java.util.List;

import org.apache.logging.log4j.core.util.OptionConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.domain.operationconfig.gateway.OperationConfigGateway;
import com.easysoftware.infrastructure.mapper.OperationConfigDOMapper;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.converter.OperationConfigConverter;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;

@Service
public class OperationConfigGatewayImpl implements OperationConfigGateway {
    Logger logger = LoggerFactory.getLogger(OperationConfigGatewayImpl.class);
    @Autowired
    private OperationConfigDOMapper mapper;

    public boolean insertOperationConfig(InputOperationConfig input) {
        OperationConfigDO operationConfigDO = OperationConfigConverter.toDataObject(input);
        int mark = mapper.insert(operationConfigDO);
        if (mark == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean truncateTable() {
        try {
            mapper.myDelete();
            return true;
        } catch (Exception e) {
            logger.info(MessageCode.EC0005.getMsgEn());
            return false;
        }
       
    }

    @Override
    public List<OperationConfigVo> selectAll() {
        List<OperationConfigDO> doList = mapper.selectList(null);
        List<OperationConfigVo> res = OperationConfigConverter.toVo(doList);
        return res;
    }
}
