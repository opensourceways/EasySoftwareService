package com.easysoftware.adapter.execute;

import com.easysoftware.application.applicationversion.ApplicationVersionService;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;
import com.easysoftware.common.aop.LimitRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appVersion")
public class ApplicationVersionExecuteAdapter {

    /**
     *Autowired instance of ApplicationVersionService for handling application package operations.
     */
    @Autowired
    private ApplicationVersionService appVersionService;

    /**
     * Inserts a new application version.
     *
     * @param inputAppVersion The input data for the new application version.
     * @return ResponseEntity<Object>.
     */
    @PostMapping
    @LimitRequest
    public ResponseEntity<Object> insertAppVersion(@Valid @RequestBody final InputApplicationVersion inputAppVersion) {
        return appVersionService.insertAppVersion(inputAppVersion);
    }


    /**
     * Updates an existing application version with the provided input data.
     *
     * @param inputAppVersion The input data for updating the application version.
     * @return ResponseEntity<Object>.
     */
    @PutMapping
    @LimitRequest
    public ResponseEntity<Object> updateAppVersion(@Valid @RequestBody final InputApplicationVersion inputAppVersion) {
        return appVersionService.updateAppVersion(inputAppVersion);
    }

    /**
     * Deletes application versions based on the provided list of names.
     *
     * @param names names List of names of application versions to be deleted.
     * @return ResponseEntity<Object>.
     */
    @DeleteMapping(value = "/{names}")
    @LimitRequest
    public ResponseEntity<Object> deleteAppVersion(@PathVariable final List<String> names) {
        return appVersionService.deleteAppVersion(names);
    }
}
