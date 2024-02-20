package com.easysoftware.domain.applicationpackage.gateway;

import java.util.List;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;

public interface ApplicationPackageGateway {
    boolean existApp(String name);
    boolean save(ApplicationPackage appPkg);
    boolean update(ApplicationPackage appPkg);
    boolean delete(List<String> names);
    List<ApplicationPackage> queryByName(ApplicationPackageSearchCondition condition);
}

