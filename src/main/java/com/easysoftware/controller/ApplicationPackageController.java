package com.easysoftware.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.entity.dto.ApplicationPackageSearchCondition;
import com.easysoftware.entity.dto.InputApplicationPackage;
import com.easysoftware.service.ApplicationPackageService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/apppkg")
public class ApplicationPackageController {
    @Autowired
    private ApplicationPackageService appPkgService;

    @PostMapping("")
    public String insertAppPkg(@Valid @RequestBody InputApplicationPackage inputAppPackage) {
        String res = appPkgService.insertAppPkg(inputAppPackage);
        return res;
    }

    @PutMapping()
    public String updateAppPkg(@RequestBody InputApplicationPackage inputAppPackage) {
        String res = appPkgService.updateAppPkg(inputAppPackage);
        return res;
    }

    @DeleteMapping(value = "/{names}")
    public String deleteAppPkg(@PathVariable List<String> names) {
        String res = appPkgService.deleteAppPkg(names);
        return res;
    }

    @GetMapping()
    public String searchAppPkg(@Valid ApplicationPackageSearchCondition condition) {
        String res = appPkgService.searchAppPkg(condition);
        return res;
    }
    
}
