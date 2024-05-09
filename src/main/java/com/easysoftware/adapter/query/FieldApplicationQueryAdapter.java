package com.easysoftware.adapter.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.filedapplication.FieldApplicationService;
import com.easysoftware.application.filedapplication.dto.FieldColumnSearchCondition;
import com.easysoftware.application.filedapplication.dto.FieldDetailSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.common.aop.RequestLimitRedis;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/field")
public class FieldApplicationQueryAdapter {
    /**
     * Health status.
     */
    private static final String HEALTH_STATUS = "health";

    /**
     * Autowired service for handling field application-related operations.
     */
    @Autowired
    private FieldApplicationService service;

    /**
     * Endpoint to search for fields based on the provided search condition.
     *
     * @param condition The search condition for querying fields.
     * @return ResponseEntity<Object>.
     */
    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchField(@Valid final FiledApplicationSerachCondition condition) {
        return service.queryMenuByName(condition);
    }

    /**
     * Endpoint to search for field columns.
     *
     * @param condition The search condition for querying field columns.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/column")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchColumn(@Valid final FieldColumnSearchCondition condition) {
        return service.searchColumn(condition);
    }

    /**
     * Endpoint to search for field details.
     *
     * @param condition The search condition for querying field details.
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/detail")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchDetail(@Valid final FieldDetailSearchCondition condition) {
        return service.queryDetailByName(condition);
    }

    /**
     * Endpoint to search for field statistics.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/stat")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchStat() {
        return service.queryStat();
    }

    /**
     * Endpoint to health check.
     *
     * @return ResponseEntity<Object>.
     */
    @GetMapping("/health")
    @RequestLimitRedis()
    public String checkHealth() {
        return HEALTH_STATUS;
    }
}
