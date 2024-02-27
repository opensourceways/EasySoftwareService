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

import com.easysoftware.aop.LimitRequest;
import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appVersion")
public class ApplicationVersionExecuteAdapter {
    @Autowired
    private ApplicationVersionService appVersionService;

    @PostMapping("")
    @LimitRequest(callTime = 10, callCount = 30)
    public ResponseEntity<Object> insertAppVersion(@Valid @RequestBody InputApplicationVersion inputAppVersion) {
        ResponseEntity<Object> res = appVersionService.insertAppVersion(inputAppVersion);
        return res;
    }

    @PutMapping()
    @LimitRequest()
    public ResponseEntity<Object> updateAppVersion(@Valid @RequestBody InputApplicationVersion inputAppVersion) {
        ResponseEntity<Object> res = appVersionService.updateAppVersion(inputAppVersion);
        return res;
    }

    @DeleteMapping(value = "/{names}")
    @LimitRequest()
    public ResponseEntity<Object> deleteAppVersion(@PathVariable List<String> names) {
        ResponseEntity<Object> res = appVersionService.deleteAppVersion(names);
        return res;
    }
}
