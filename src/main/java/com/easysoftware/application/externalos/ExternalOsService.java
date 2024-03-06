package com.easysoftware.application.externalos;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.application.externalos.dto.InputExternalOs;

public interface ExternalOsService {
    ResponseEntity<Object> searchPkgMap(ExternalOsSearchCondiiton condition);
    ResponseEntity<Object> insertPkgMap(InputExternalOs input);
    ResponseEntity<Object> updatePkgMap(InputExternalOs input);
    ResponseEntity<Object> deletePkgMap(List<String> ids);

}
