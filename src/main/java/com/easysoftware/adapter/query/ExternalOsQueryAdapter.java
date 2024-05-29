package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.easysoftware.application.externalos.ExternalOsService;
import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/externalos")
public class ExternalOsQueryAdapter {

    /**
     * Autowired service for interacting with external operating systems.
     */
    @Autowired
    private ExternalOsService externalOsService;


    /**
     * Handles a request to search package mappings in the external operating system.
     *
     * @param condition The search condition for querying package mappings.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchPkgMap(@Valid final ExternalOsSearchCondiiton condition) {
        return externalOsService.searchPkgMap(condition);
    }
}