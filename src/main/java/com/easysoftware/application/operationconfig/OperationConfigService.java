package com.easysoftware.application.operationconfig;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.operationconfig.dto.InputOperationConfig;

public interface OperationConfigService {
    boolean insertOperationConfig(InputOperationConfig input);
    boolean truncateTable();
}
