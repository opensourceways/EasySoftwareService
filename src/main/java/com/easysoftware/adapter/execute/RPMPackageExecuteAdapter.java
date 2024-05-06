package com.easysoftware.adapter.execute;

import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
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
@RequestMapping("/rpmpkg")
public class RPMPackageExecuteAdapter {

    /**
     * Autowired service for handling RPM package operations.
     */
    @Autowired
    private RPMPackageService rPMPkgService;

    /**
     * Endpoint to insert a new RPM package.
     *
     * @param inputrPMPackage The input data for inserting a new RPM package.
     * @return ResponseEntity<Object>.
     */
    @PostMapping("")
    public ResponseEntity<Object> insertRPMPkg(@Valid @RequestBody final InputRPMPackage inputrPMPackage) {
        return rPMPkgService.insertRPMPkg(inputrPMPackage);
    }


    /**
     * Endpoint to insert a new RPM package.
     *
     * @param inputrPMPackage The input data for inserting a new RPM package.
     * @return ResponseEntity<Object>.
     */
    @PutMapping()
    public ResponseEntity<Object> updateRPMPkg(@Valid @RequestBody final InputRPMPackage inputrPMPackage) {
        return rPMPkgService.updateRPMPkg(inputrPMPackage);
    }


    /**
     * Endpoint to delete RPM packages associated with specified IDs.
     *
     * @param ids List of IDs of RPM packages to be deleted.
     * @return ResponseEntity<Object>.
     */
    @DeleteMapping(value = "/{ids}")
    public ResponseEntity<Object> deleteRPMPkg(@PathVariable final List<String> ids) {
        return rPMPkgService.deleteRPMPkg(ids);
    }
}
