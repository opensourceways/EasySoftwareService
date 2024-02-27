package com.easysoftware.application.domainpackage;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;

public interface DomainPackageService {
    ResponseEntity<Object> searchDomain(DomainSearchCondition condition);
}
