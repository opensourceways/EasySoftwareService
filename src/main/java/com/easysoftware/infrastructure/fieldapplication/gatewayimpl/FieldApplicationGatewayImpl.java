package com.easysoftware.infrastructure.fieldapplication.gatewayimpl;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter.FieldApplicationConverter;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject.FieldApplicationDO;
import com.easysoftware.infrastructure.mapper.FieldApplicationDOMapper;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FieldApplicationGatewayImpl implements FieldapplicationGateway {
    @Autowired
    private FieldApplicationDOMapper fieldAppMapper;

    private static final Logger logger = LoggerFactory.getLogger(FieldApplicationGatewayImpl.class);

    @Override
    public List<FiledApplicationVo> queryAll(FiledApplicationSerachCondition condition){
        QueryWrapper<FieldApplicationDO> wrapper = new QueryWrapper<>();
        List<FieldApplicationDO> list = fieldAppMapper.selectList(wrapper);
        return FieldApplicationConverter.toVo(list);
    }

}
