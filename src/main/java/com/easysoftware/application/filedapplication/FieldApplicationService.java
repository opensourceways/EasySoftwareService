package com.easysoftware.application.filedapplication;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;

public interface FieldApplicationService {
    ResponseEntity<Object> queryAll(FiledApplicationSerachCondition condition);
}
