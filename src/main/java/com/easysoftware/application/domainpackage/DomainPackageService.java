package com.easysoftware.application.domainpackage;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.domainpackage.dto.DomainColumnCondition;
import com.easysoftware.application.domainpackage.dto.DomainDetailSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;

public interface DomainPackageService {
    ResponseEntity<Object> searchDomain(DomainSearchCondition condition);
    ResponseEntity<Object> searchDomainDetail(DomainDetailSearchCondition condition);
    ResponseEntity<Object> searchColumn(DomainColumnCondition condition);
    ResponseEntity<Object> queryStat();
}
