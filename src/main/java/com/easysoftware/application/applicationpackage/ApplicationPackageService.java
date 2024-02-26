package com.easysoftware.application.applicationpackage;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ApplicationPackageService {
    ResponseEntity<Object> insertAppPkg(InputApplicationPackage listApp);
    ResponseEntity<Object> updateAppPkg(InputApplicationPackage inputAppPackage);
    ResponseEntity<Object> deleteAppPkg(List<String> names);
    ResponseEntity<Object> searchAppPkg(ApplicationPackageSearchCondition condition);
    ResponseEntity<Object> searchAppPkgIcon(String name);
}
