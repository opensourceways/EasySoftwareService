package com.easysoftware.application.filedapplication;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.filedapplication.dto.FieldColumnSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;

@Service
public class FieldApplicationServiceImpl implements FieldApplicationService {
    @Autowired
    FieldapplicationGateway gateway;

    @Override
    public ResponseEntity<Object> queryAll(FiledApplicationSerachCondition condition) {
        Map<String, Object> res = gateway.queryAll(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> searchColumn(FieldColumnSearchCondition condition) {
        List<String> columns = QueryWrapperUtil.splitStr(condition.getColumn());
        Map<String, List<String>> res = gateway.queryColumn(columns);
        return ResultUtil.success(HttpStatus.OK, res);
    }
    
}
