package com.easysoftware.adapter.execute;

import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
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
@RequestMapping("/epkgpkg")
public class EPKGPackageExecuteAdapter {

    /**
     * Autowired instance of EPKGPackageService for handling application package operations.
     */
    @Autowired
    private EPKGPackageService epkgPackageService;

    /**
     * insert EPKG Package.
     *
     * @param inputEPKGPackage The input EPKG Package details to be inserted.
     * @return ResponseEntity<Object>.
     */
    @PostMapping
    public ResponseEntity<Object> insertEPKGPkg(@Valid @RequestBody final InputEPKGPackage inputEPKGPackage) {
        return epkgPackageService.insertEPKGPkg(inputEPKGPackage);
    }

    /**
     * update EPKG Package.
     *
     * @param inputEPKGPackage The input EPKG Package details to be updated.
     * @return ResponseEntity<Object>.
     */
    @PutMapping
    public ResponseEntity<Object> updateEPKGPkg(@Valid @RequestBody final InputEPKGPackage inputEPKGPackage) {
        return epkgPackageService.updateEPKGPkg(inputEPKGPackage);
    }

    /**
     *  delete EPKG Packages by their Ids.
     *
     * @param ids List of IDs of the EPKG Packages to be deleted.
     * @return ResponseEntity<Object>.
     */
    @DeleteMapping(value = "/{ids}")
    public ResponseEntity<Object> deleteEPKGPkg(@PathVariable final List<String> ids) {
        return epkgPackageService.deleteEPKGPkg(ids);
    }
}
