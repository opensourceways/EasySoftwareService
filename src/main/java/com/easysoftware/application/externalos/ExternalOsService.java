package com.easysoftware.application.externalos;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import org.springframework.http.ResponseEntity;


public interface ExternalOsService {

    /**
     * Search for package mappings based on the specified search conditions.
     *
     * @param condition The search conditions to filter package mappings.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchPkgMap(ExternalOsSearchCondiiton condition);
}
