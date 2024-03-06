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
import com.easysoftware.common.utils.Base64Util;
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
        List<String> existedNames = new ArrayList<>();
        for (String id : ids) {
            boolean found = externalOsGateway.existExternalOs(id);
            if (found) {
                existedNames.add(id);
            }
        }

        List<String> deletedNames = new ArrayList<>();
        for (String id : existedNames) {
            boolean deleted = externalOsGateway.delete(id);
            if (deleted) {
                deletedNames.add(id);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , ids.toString(), existedNames.toString(), deletedNames.toString());
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> insertPkgMap(InputExternalOs input) {
        input = Base64Util.decode(input);

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
        input = Base64Util.decode(input);

        if (StringUtils.isBlank(input.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }
        // 若数据库中不存在，则请求失败
        boolean found = externalOsGateway.existExternalOs(input.getId());
        if (!found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }

        ExternalOs ex = new ExternalOs();
        BeanUtils.copyProperties(input, ex);

        boolean succeed = externalOsGateway.update(ex);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return ResultUtil.success(HttpStatus.OK);
    }
    
}
