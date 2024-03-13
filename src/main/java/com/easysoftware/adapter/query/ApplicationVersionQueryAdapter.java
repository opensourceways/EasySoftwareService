package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.aop.LimitRequest;
import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.common.interceptor.CompatibleToken;
import com.easysoftware.common.interceptor.OneidToken;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appVersion")
public class ApplicationVersionQueryAdapter {
    @Autowired
    private ApplicationVersionService appVersionService;

    @GetMapping()
    @LimitRequest()
    // @OneidToken
    // @CompatibleToken
    public ResponseEntity<Object> searchAppVersion(@Valid ApplicationVersionSearchCondition condition) {
        ResponseEntity<Object> res = appVersionService.searchAppVersion(condition);
        return res;
    }
    
}
