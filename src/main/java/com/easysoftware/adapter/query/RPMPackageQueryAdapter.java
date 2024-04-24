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
    @RequestLimitRedis() //dos-global设置 2s内 单一ip调用超5次触发
    public ResponseEntity<Object> searchRPMPkg(@Valid RPMPackageSearchCondition condition) {
        ResponseEntity<Object> res = rPMPkgService.searchRPMPkg(condition);
        return res;
    }
}
