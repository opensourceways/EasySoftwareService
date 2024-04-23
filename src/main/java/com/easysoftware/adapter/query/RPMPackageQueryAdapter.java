package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rpmpkg")
public class RPMPackageQueryAdapter {
    @Autowired
    private RPMPackageService rPMPkgService;

    @GetMapping()
    @RequestLimitRedis(period = 10, count = 5) // 10s内同一ip连续访问5次，拒绝访问
    public ResponseEntity<Object> searchRPMPkg(@Valid RPMPackageSearchCondition condition) {
        ResponseEntity<Object> res = rPMPkgService.searchRPMPkg(condition);
        return res;
    }
}
