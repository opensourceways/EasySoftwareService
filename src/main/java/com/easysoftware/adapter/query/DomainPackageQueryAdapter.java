package com.easysoftware.adapter.query;

import java.security.DrbgParameters.Reseed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.domainpackage.DomainPackageService;
import com.easysoftware.application.domainpackage.dto.DomainColumnCondition;
import com.easysoftware.application.domainpackage.dto.DomainDetailSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import okhttp3.Response;

@RestController
@RequestMapping("/domain")
public class DomainPackageQueryAdapter {
    @Autowired
    private DomainPackageService domainService;
    
    @GetMapping()
    @RequestLimitRedis() 
    public ResponseEntity<Object> queryByName(@Valid DomainSearchCondition condition) {
        ResponseEntity<Object> res = domainService.searchDomain(condition);
        return res;
    }

    @GetMapping("/detail")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryDomainDetail(@Valid DomainDetailSearchCondition condition) {
        ResponseEntity<Object> res = domainService.searchDomainDetail(condition);
        return res;
    }

    @GetMapping("/column")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryColumn(@Valid DomainColumnCondition condition) {
        ResponseEntity<Object> res = domainService.searchColumn(condition);
        return res;
    }

    @GetMapping("/stat")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryStat() {
        ResponseEntity<Object> res = domainService.queryStat();
        return res;
    }


}
