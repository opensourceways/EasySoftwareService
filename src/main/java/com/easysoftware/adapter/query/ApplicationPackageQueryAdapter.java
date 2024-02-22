package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/apppkg")
public class ApplicationPackageQueryAdapter {
    @Autowired
    private ApplicationPackageService appPkgService;

    @GetMapping()
    public ResponseEntity<Object> searchAppPkg(@Valid ApplicationPackageSearchCondition condition) {
        ResponseEntity<Object> res = appPkgService.searchAppPkg(condition);
        return res;
    }
}
