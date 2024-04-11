package com.easysoftware.infrastructure.operationconfig.gatewayimpl.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.operationconfig.dto.InputOperationConfig;
import com.easysoftware.application.operationconfig.vo.OperationConfigVo;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;

public class OperationConfigConverter {
    public static OperationConfigDO toDataObject(InputOperationConfig input) {
        OperationConfigDO operationConfigDO = new OperationConfigDO();
        BeanUtils.copyProperties(input, operationConfigDO);
        return operationConfigDO;
    }

    public static List<OperationConfigVo> toVo(List<OperationConfigDO> doList) {
        List<OperationConfigVo> res = new ArrayList<>();
        for (OperationConfigDO op : doList) {
            OperationConfigVo vo = toVo(op);
            res.add(vo);
        }
        return res;
    }

    public static OperationConfigVo toVo(OperationConfigDO opDo) {
        OperationConfigVo opVo = new OperationConfigVo();
        BeanUtils.copyProperties(opDo, opVo);
        return opVo;
    }

    
}
