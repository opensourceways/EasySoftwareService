package com.easysoftware.adapter.execute;

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
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
@RequestMapping("/apppkg")
public class ApplicationPackageExecuteAdapter {

    /**
     * Autowired instance of ApplicationPackageService for handling application package operations.
     */
    @Autowired
    private ApplicationPackageService appPkgService;

    /**
     * Insert AppPkg info.
     *
     * @param inputAppPackage AppPkg information entity class.
     * @return ResponseEntity<Object>.
     */
    @PostMapping
    public ResponseEntity<Object> insertAppPkg(@Valid @RequestBody final InputApplicationPackage inputAppPackage) {
        return appPkgService.insertAppPkg(inputAppPackage);
    }

    /**
     * Update AppPkg info.
     *
     * @param inputAppPackage AppPkg information entity class.
     * @return ResponseEntity<Object>.
     */
    @PutMapping
    public ResponseEntity<Object> updateAppPkg(@Valid @RequestBody final InputApplicationPackage inputAppPackage) {
        return appPkgService.updateAppPkg(inputAppPackage);
    }

    /**
     * Delete AppPkg info.
     *
     * @param names AppPkg name collection.
     * @return ResponseEntity<Object>.
     */
    @DeleteMapping(value = "/{names}")
    public ResponseEntity<Object> deleteAppPkg(@PathVariable final List<String> names) {
        return appPkgService.deleteAppPkg(names);
    }
}
