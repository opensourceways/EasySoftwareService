package com.easysoftware.adapter.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appVersion")
public class ApplicationVersionAdapter {
    @Autowired
    private ApplicationVersionService appVersionService;

    @PostMapping("")
    public ResponseEntity<Object> insertAppVersion(@Valid @RequestBody InputApplicationVersion inputAppVersion) {
        ResponseEntity<Object> res = appVersionService.insertAppVersion(inputAppVersion);
        return res;
    }

    @PutMapping()
    public ResponseEntity<Object> updateAppVersion(@Valid @RequestBody InputApplicationVersion inputAppVersion) {
        ResponseEntity<Object> res = appVersionService.updateAppVersion(inputAppVersion);
        return res;
    }

    @DeleteMapping(value = "/{names}")
    public ResponseEntity<Object> deleteAppVersion(@PathVariable List<String> names) {
        ResponseEntity<Object> res = appVersionService.deleteAppVersion(names);
        return res;
    }

    @GetMapping()
    public ResponseEntity<Object> searchAppVersion(@Valid ApplicationVersionSearchCondition condition) {
        ResponseEntity<Object> res = appVersionService.searchAppVersion(condition);
        return res;
    }
    
}
