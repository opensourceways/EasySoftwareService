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

    /**
     * Autowired service for handling RPM package-related operations.
     */
    @Autowired
    private RPMPackageService rPMPkgService;

    /**
     * Endpoint to search for RPM packages based on the provided search condition.
     *
     * @param condition The search condition for querying RPM packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis() // dos-global设置 2s内 单一ip调用超5次触发
    public ResponseEntity<Object> searchRPMPkg(@Valid final RPMPackageSearchCondition condition) {
        return rPMPkgService.searchRPMPkg(condition);
    }

    /**
     * Endpoint to query for all avalaible openEuler version of RPM packages based
     * on the provided search condition.
     *
     * @param condition The search condition for querying RPM packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/eulerver")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryEulerVersionsByName(@Valid final RPMPackageSearchCondition condition) {
        return rPMPkgService.queryEulerVersionsByName(condition);
    }

    /**
     * Endpoint to query for all avalaible openEuler arch of RPM packages based
     * on the provided search condition.
     *
     * @param condition The search condition for querying RPM packages.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/eulerarch")
    @RequestLimitRedis()
    public ResponseEntity<Object> queryEulerArchsByName(@Valid final RPMPackageSearchCondition condition) {
        return rPMPkgService.queryEulerArchsByName(condition);
    }
}
