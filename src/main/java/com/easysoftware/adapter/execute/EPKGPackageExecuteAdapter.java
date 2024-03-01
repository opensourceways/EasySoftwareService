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

import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/epkgpkg")
public class EPKGPackageExecuteAdapter {
    @Autowired
    private EPKGPackageService epkgPackageService;

    @PostMapping("")
    public ResponseEntity<Object> insertEPKGPkg(@Valid @RequestBody InputEPKGPackage inputEPKGPackage) {
        ResponseEntity<Object> res = epkgPackageService.insertEPKGPkg(inputEPKGPackage);
        return res;
    }

    @PutMapping()
    public ResponseEntity<Object> updateEPKGPkg(@Valid @RequestBody InputEPKGPackage inputEPKGPackage) {
        ResponseEntity<Object> res = epkgPackageService.updateEPKGPkg(inputEPKGPackage);
        return res;
    }

    @DeleteMapping(value = "/{ids}")
    public ResponseEntity<Object> deleteEPKGPkg(@PathVariable List<String> ids) {
        ResponseEntity<Object> res = epkgPackageService.deleteEPKGPkg(ids);
        return res;
    }
}
