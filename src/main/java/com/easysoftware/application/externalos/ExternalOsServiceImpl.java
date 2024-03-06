package com.easysoftware.application.externalos;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.externalos.ExternalOs;
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
    
}
