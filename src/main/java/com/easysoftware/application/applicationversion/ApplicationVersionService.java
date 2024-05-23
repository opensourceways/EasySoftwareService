package com.easysoftware.application.applicationversion;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.applicationversion.dto.ApplicationColumnSearchCondition;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import org.springframework.http.ResponseEntity;

public interface ApplicationVersionService extends BaseIService<ApplicationVersionDO> {
    /**
     * Searches for application versions based on the specified search conditions.
     *
     * @param condition The search conditions to filter application versions.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchAppVersion(ApplicationVersionSearchCondition condition);

    /**
     * Searches for column based on the specified search conditions.
     *
     * @param condition The search conditions to filter application versions.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchAppVerColumn(ApplicationColumnSearchCondition condition);
}
