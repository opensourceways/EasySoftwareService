package com.easysoftware.application.externalos;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.application.externalos.dto.InputExternalOs;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExternalOsService {

    /**
     * Search for package mappings based on the specified search conditions.
     *
     * @param condition The search conditions to filter package mappings.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchPkgMap(ExternalOsSearchCondiiton condition);

    /**
     * Insert a new package mapping using the provided input data.
     *
     * @param input The input data for creating a new package mapping.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> insertPkgMap(InputExternalOs input);

    /**
     * Update an existing package mapping with the provided input data.
     *
     * @param input The input data for updating an existing package mapping.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> updatePkgMap(InputExternalOs input);

    /**
     * Delete package mappings associated with the specified list of IDs.
     *
     * @param ids List of IDs of package mappings to be deleted.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> deletePkgMap(List<String> ids);


}
