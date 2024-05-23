package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appVersion")
public class ApplicationVersionQueryAdapter {
    /**
     * Autowired service for handling application version-related operations.
     */
    @Autowired
    private ApplicationVersionService appVersionService;

    /**
     * Endpoint to search for application versions based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application versions.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchAppVersion(@Valid final ApplicationVersionSearchCondition condition) {
        return appVersionService.searchAppVersion(condition);
    }

    /**
     * Endpoint to search for application versions based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application versions.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/column")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchAppVerColumn(@Valid final ApplicationVersionSearchCondition condition) {
        return appVersionService.searchAppVerColumn(condition);
    }
}
