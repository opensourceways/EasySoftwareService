package com.easysoftware.infrastructure.mapper;

import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easysoftware.infrastructure.operationconfig.gatewayimpl.dataobject.OperationConfigDO;

public interface OperationConfigDOMapper extends BaseMapper<OperationConfigDO> {
    
    @Update("truncate table operation_config")
    void myDelete();
}
