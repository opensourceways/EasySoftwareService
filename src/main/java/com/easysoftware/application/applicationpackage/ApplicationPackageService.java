package com.easysoftware.application.applicationpackage;

import java.util.List;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;

public interface ApplicationPackageService {
    String insertAppPkg(InputApplicationPackage listApp);
    String updateAppPkg(InputApplicationPackage inputAppPackage);
    String deleteAppPkg(List<String> names);
    String searchAppPkg(ApplicationPackageSearchCondition condition);
}
