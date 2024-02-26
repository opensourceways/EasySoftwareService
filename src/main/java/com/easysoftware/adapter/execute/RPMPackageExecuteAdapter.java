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

import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rpmpkg")
public class RPMPackageExecuteAdapter {
    @Autowired
    private RPMPackageService rPMPkgService;

    @PostMapping("")
    public ResponseEntity<Object> insertRPMPkg(@Valid @RequestBody InputRPMPackage inputrPMPackage) {
        ResponseEntity<Object> res = rPMPkgService.insertRPMPkg(inputrPMPackage);
        return res;
    }

    @PutMapping()
    public ResponseEntity<Object> updateRPMPkg(@Valid @RequestBody InputRPMPackage inputrPMPackage) {
        ResponseEntity<Object> res = rPMPkgService.updateRPMPkg(inputrPMPackage);
        return res;
    }

    @DeleteMapping(value = "/{ids}")
    public ResponseEntity<Object> deleteRPMPkg(@PathVariable List<String> ids) {
        ResponseEntity<Object> res = rPMPkgService.deleteRPMPkg(ids);
        return res;
    }
}
