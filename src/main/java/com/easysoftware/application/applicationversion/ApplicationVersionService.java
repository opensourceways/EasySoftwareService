package com.easysoftware.application.applicationversion;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationVersionService extends BaseIService<ApplicationVersionDO> {
    /**
     * Inserts a new application version based on the provided input.
     *
     * @param listApp The input application version to be inserted.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> insertAppVersion(InputApplicationVersion listApp);

    /**
     * Updates an existing application version using the provided input.
     *
     * @param inputAppVersion The updated application version information.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> updateAppVersion(InputApplicationVersion inputAppVersion);

    /**
     * Deletes application versions based on the provided list of names.
     *
     * @param names List of names of application versions to be deleted.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> deleteAppVersion(List<String> names);

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
    ResponseEntity<Object> searchAppVerColumn(ApplicationVersionSearchCondition condition);
}
