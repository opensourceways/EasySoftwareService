package com.easysoftware.domain.applicationversion.gateway;

import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;

import java.util.Collection;
import java.util.Map;


public interface ApplicationVersionGateway {
    /**
     * Check if an application exists based on its name.
     *
     * @param name The name of the application
     * @return true if the application exists, false otherwise
     */
    boolean existApp(String name);

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    Map<String, Object> queryByName(ApplicationVersionSearchCondition condition);

    /**
     * Convert a batch of data objects to ApplicationVersionDO objects.
     *
     * @param dataObject A collection of data objects to convert
     * @return A collection of ApplicationVersionDO objects
     */
    Collection<ApplicationVersionDO> convertBatch(Collection<String> dataObject);
}

