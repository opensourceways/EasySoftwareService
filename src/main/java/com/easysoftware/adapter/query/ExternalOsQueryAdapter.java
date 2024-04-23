package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.externalos.ExternalOsService;
import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/externalos")
public class ExternalOsQueryAdapter {
    @Autowired
    private ExternalOsService externalOsService;

    @GetMapping()
    @RequestLimitRedis() 
    public ResponseEntity<Object> searchPkgMap(@Valid ExternalOsSearchCondiiton condition) {
        ResponseEntity<Object> res = externalOsService.searchPkgMap(condition);
        return res;
    }
}
