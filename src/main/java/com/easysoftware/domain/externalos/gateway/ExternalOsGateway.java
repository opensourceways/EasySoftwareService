package com.easysoftware.domain.externalos.gateway;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.domain.externalos.ExternalOsUnique;

import java.util.List;
import java.util.Map;

public interface ExternalOsGateway {
    /**
     * Query package mapping based on the provided search condition.
     *
     * @param condition The search condition for querying package mappings
     * @return A map containing relevant information
     */
    Map<String, Object> queryPkgMap(ExternalOsSearchCondiiton condition);

    /**
     * Check if an external operating system exists based on its ID.
     *
     * @param id The ID of the external operating system
     * @return true if the external operating system exists, false otherwise
     */
    boolean existExternalOs(String id);

    /**
     * Delete external operating systems by their IDs.
     *
     * @param ids A list of IDs of external operating systems to delete
     * @return the number of rows deleted
     */
    int delete(List<String> ids);

    /**
     * Check if an external operating system exists based on its unique identifier.
     *
     * @param uni The unique identifier of the external operating system
     * @return true if the external operating system exists, false otherwise
     */
    boolean existExternalOs(ExternalOsUnique uni);

    /**
     * Save an ExternalOs object.
     *
     * @param ex The ExternalOs object to save
     * @return true if the save operation was successful, false otherwise
     */
    boolean save(ExternalOs ex);

    /**
     * Update an existing ExternalOs object.
     *
     * @param ex The ExternalOs object to update
     * @return the number of rows affected by the update operation
     */
    int update(ExternalOs ex);

}
