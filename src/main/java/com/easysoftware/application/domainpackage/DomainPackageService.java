package com.easysoftware.application.domainpackage;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.domainpackage.dto.DomainColumnCondition;
import com.easysoftware.application.domainpackage.dto.DomainDetailSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;

public interface DomainPackageService {
    /**
     * Search for domains based on the provided search condition.
     *
     * @param condition The DomainSearchCondition for searching.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchDomain(DomainSearchCondition condition);

    /**
     * Search for domain details based on the provided search condition.
     *
     * @param condition The DomainDetailSearchCondition for searching.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchDomainDetail(DomainDetailSearchCondition condition);

    /**
     * Search for columns based on the provided condition.
     *
     * @param condition The DomainColumnCondition for searching.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchColumn(DomainColumnCondition condition);

    /**
     * Query statistics.
     *
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryStat();

}
