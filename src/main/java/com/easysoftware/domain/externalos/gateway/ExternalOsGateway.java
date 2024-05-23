package com.easysoftware.domain.externalos.gateway;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.domain.externalos.ExternalOsUnique;

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
     * Check if an external operating system exists based on its unique identifier.
     *
     * @param uni The unique identifier of the external operating system
     * @return true if the external operating system exists, false otherwise
     */
    boolean existExternalOs(ExternalOsUnique uni);
}
