package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.domainpackage.DomainPackageService;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/domain")
public class DomainPackageQueryAdapter {
    @Autowired
    private DomainPackageService domainService;

    @GetMapping()
    public ResponseEntity<Object> queryByName(@Valid DomainSearchCondition condition) {
        ResponseEntity<Object> res = domainService.searchDomain(condition);
        return res;
    }
}
