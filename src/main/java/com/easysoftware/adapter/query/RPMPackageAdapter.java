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

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rpmpkg")
public class RPMPackageAdapter {
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

    @DeleteMapping(value = "/{names}")
    public ResponseEntity<Object> deleteRPMPkg(@PathVariable List<String> names) {
        ResponseEntity<Object> res = rPMPkgService.deleteRPMPkg(names);
        return res;
    }

    @GetMapping()
    public ResponseEntity<Object> searchRPMPkg(@Valid RPMPackageSearchCondition condition) {
        ResponseEntity<Object> res = rPMPkgService.searchRPMPkg(condition);
        return res;
    }
}
