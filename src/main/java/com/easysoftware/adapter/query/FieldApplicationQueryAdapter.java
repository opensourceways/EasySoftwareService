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
    @Autowired
    private FieldApplicationService service;

    @GetMapping()
    @RequestLimitRedis()
    public ResponseEntity<Object> searchField(@Valid FiledApplicationSerachCondition condition) {
        return service.queryMenuByName(condition);
    }

    @GetMapping("/column")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchColumn(@Valid FieldColumnSearchCondition condition) {
        return service.searchColumn(condition);
    }

    @GetMapping("/detail")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchDetail(@Valid FieldDetailSearchCondition condition) {
        return service.queryDetailByName(condition);
    }

    @GetMapping("/stat")
    @RequestLimitRedis()
    public ResponseEntity<Object> searchStat() {
        return service.queryStat();
    }
}
