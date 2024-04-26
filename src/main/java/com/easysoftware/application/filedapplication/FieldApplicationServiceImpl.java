package com.easysoftware.application.filedapplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;

@Service
public class FieldApplicationServiceImpl implements FieldApplicationService {
    @Autowired
    FieldapplicationGateway gateway;

    @Override
    public ResponseEntity<Object> queryAll(FiledApplicationSerachCondition condition) {
        List<FiledApplicationVo> res = gateway.queryAll(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
    
}
