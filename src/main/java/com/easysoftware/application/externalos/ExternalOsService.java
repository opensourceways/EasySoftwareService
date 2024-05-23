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
}
