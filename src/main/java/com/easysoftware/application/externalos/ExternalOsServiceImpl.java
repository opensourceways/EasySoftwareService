package com.easysoftware.application.externalos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.application.externalos.dto.InputExternalOs;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.domain.externalos.ExternalOsUnique;
import com.easysoftware.domain.externalos.gateway.ExternalOsGateway;

import jakarta.annotation.Resource;

@Service
public class ExternalOsServiceImpl implements ExternalOsService {
    @Resource
    ExternalOsGateway externalOsGateway;

    @Override
    public ResponseEntity<Object> searchPkgMap(ExternalOsSearchCondiiton condition) {
        Map<String, Object> res = externalOsGateway.queryPkgMap(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> deletePkgMap(List<String> ids) {
        int mark = externalOsGateway.delete(ids);
        String msg = String.format("the number of deleted : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    @Override
    public ResponseEntity<Object> insertPkgMap(InputExternalOs input) {
        // 若数据库中已经存在该数据，则请求失败
        if (StringUtils.isNotBlank(input.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }

        ExternalOs ex = new ExternalOs();
        BeanUtils.copyProperties(input, ex);
        
        boolean succeed = externalOsGateway.save(ex);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updatePkgMap(InputExternalOs input) {
        ExternalOs ex = new ExternalOs();
        BeanUtils.copyProperties(input, ex);

        int mark = externalOsGateway.update(ex);
        String msg = String.format("the number of updated : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }
    
}
