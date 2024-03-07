package com.easysoftware.adapter.execute;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.application.externalos.ExternalOsService;
import com.easysoftware.application.externalos.dto.InputExternalOs;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/externalos")
public class ExternalOsExecuteAdapter {
    @Autowired
    private ExternalOsService externalOsService;

    @PostMapping("")
    public ResponseEntity<Object> insertPkgMap(@Valid @RequestBody InputExternalOs input) {
        ResponseEntity<Object> res = externalOsService.insertPkgMap(input);
        return res;
    }

    @PutMapping()
    public ResponseEntity<Object> updatePkgMap(@Valid @RequestBody InputExternalOs input) {
        ResponseEntity<Object> res = externalOsService.updatePkgMap(input);
        return res;
    }

    @DeleteMapping(value = "/{ids}")
    public ResponseEntity<Object> deletePkgMap(@PathVariable List<String> ids) {
        ResponseEntity<Object> res = externalOsService.deletePkgMap(ids);
        return res;
    }
}
