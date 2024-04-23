package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/epkgpkg")
public class EPKGPackageQueryAdapter {
    @Autowired
    private EPKGPackageService ePKGPackageService;

    @GetMapping()
    @RequestLimitRedis(period = 10, count = 5) // 10s内同一ip连续访问5次，拒绝访问
    public ResponseEntity<Object> searchEPKGPkg(@Valid EPKGPackageSearchCondition condition) {
        ResponseEntity<Object> res = ePKGPackageService.searchEPKGPkg(condition);
        return res;
    }
}
