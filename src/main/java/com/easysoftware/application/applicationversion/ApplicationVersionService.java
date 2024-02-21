package com.easysoftware.application.applicationversion;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;

public interface ApplicationVersionService {
    ResponseEntity<Object> insertAppVersion(InputApplicationVersion listApp);
    ResponseEntity<Object> updateAppVersion(InputApplicationVersion inputAppVersion);
    ResponseEntity<Object> deleteAppVersion(List<String> names);
    ResponseEntity<Object> searchAppVersion(ApplicationVersionSearchCondition condition);
}
