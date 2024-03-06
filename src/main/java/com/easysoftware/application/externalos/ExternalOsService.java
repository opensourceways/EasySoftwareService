package com.easysoftware.application.externalos;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;

public interface ExternalOsService {
    ResponseEntity<Object> searchPkgMap(ExternalOsSearchCondiiton condition);
}
