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

    /**
     * Autowired service for managing EPKGPackage packages.
     */
    @Autowired
    private EPKGPackageService ePKGPackageService;

    /**
     * Search for EPKG packages based on the provided search condition.
     *
     * @param condition The search condition for querying EPKG packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchEPKGPkg(@Valid final EPKGPackageSearchCondition condition) {
        return ePKGPackageService.searchEPKGPkg(condition);
    }
}
