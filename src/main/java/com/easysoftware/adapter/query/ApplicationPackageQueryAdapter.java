package com.easysoftware.adapter.query;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/apppkg")
public class ApplicationPackageQueryAdapter {
    @Autowired
    private ApplicationPackageService appPkgService;

    @GetMapping()
    public ResponseEntity<Object> searchAppPkg(@Valid ApplicationPackageSearchCondition condition) {
        ResponseEntity<Object> res = appPkgService.searchAppPkg(condition);
        return res;
    }

    @GetMapping(value = "/icon/{name}")
    public ResponseEntity<Object> searchAppPkgIcon(@NotBlank @PathVariable("name") String name) throws Exception {
        ResponseEntity<Object> res = appPkgService.searchAppPkgIcon(name);
        return res;
    }
}
