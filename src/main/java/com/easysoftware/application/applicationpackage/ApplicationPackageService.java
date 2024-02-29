package com.easysoftware.application.applicationpackage;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;

public interface ApplicationPackageService {
    ResponseEntity<Object> insertAppPkg(InputApplicationPackage listApp);
    ResponseEntity<Object> updateAppPkg(InputApplicationPackage inputAppPackage);
    ResponseEntity<Object> deleteAppPkg(List<String> names);
    ResponseEntity<Object> searchAppPkg(ApplicationPackageSearchCondition condition);
    Map<String, Object> queryAllAppPkgMenu(ApplicationPackageSearchCondition condition);
}
