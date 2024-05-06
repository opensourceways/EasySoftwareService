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
     * Save an ApplicationVersion object.
     *
     * @param appVersion The ApplicationVersion object to save
     * @return true if the save operation was successful, false otherwise
     */
    boolean save(ApplicationVersion appVersion);

    /**
     * Update an existing ApplicationVersion object.
     *
     * @param appVersion The ApplicationVersion object to update
     * @return true if the update operation was successful, false otherwise
     */
    boolean update(ApplicationVersion appVersion);

    /**
     * Delete an application by name.
     *
     * @param name The name of the application to delete
     * @return true if the delete operation was successful, false otherwise
     */
    boolean delete(String name);

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

