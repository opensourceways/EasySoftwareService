package com.easysoftware.application.filedapplication;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.filedapplication.dto.FieldColumnSearchCondition;
import com.easysoftware.application.filedapplication.dto.FieldDetailSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;

public interface FieldApplicationService {
    ResponseEntity<Object> queryMenuByName(FiledApplicationSerachCondition condition);
    ResponseEntity<Object> queryDetailByName(FieldDetailSearchCondition condition);
    ResponseEntity<Object> searchColumn(FieldColumnSearchCondition condition);
    ResponseEntity<Object> queryStat();

}
