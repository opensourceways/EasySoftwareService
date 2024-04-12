package com.easysoftware.application.applicationversion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;

public interface ApplicationVersionService extends BaseIService<ApplicationVersionDO>{
    ResponseEntity<Object> insertAppVersion(InputApplicationVersion listApp);
    ResponseEntity<Object> updateAppVersion(InputApplicationVersion inputAppVersion);
    ResponseEntity<Object> deleteAppVersion(List<String> names);
    ResponseEntity<Object> searchAppVersion(ApplicationVersionSearchCondition condition);
}
