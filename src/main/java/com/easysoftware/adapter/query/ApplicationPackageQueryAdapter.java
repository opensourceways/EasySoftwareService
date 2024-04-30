package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apppkg")
public class ApplicationPackageQueryAdapter {
    @Autowired
    private ApplicationPackageService appPkgService;

    @GetMapping("")
    @RequestLimitRedis() 
    public ResponseEntity<Object> queryByName(@Valid ApplicationPackageSearchCondition condition) {
        ResponseEntity<Object> res = appPkgService.searchAppPkg(condition);
        return res;
    }
    
    @GetMapping("/tags")
    @RequestLimitRedis() 
    public ResponseEntity<Object> queryByTags(@Valid ApplicationPackageSearchCondition condition) {
        ResponseEntity<Object> res = appPkgService.queryPkgByTags(condition);
        return res;
    }
}
