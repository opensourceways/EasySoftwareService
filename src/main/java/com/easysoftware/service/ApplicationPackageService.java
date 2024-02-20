package com.easysoftware.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.easysoftware.entity.dto.ApplicationPackageSearchCondition;
import com.easysoftware.entity.dto.InputApplicationPackage;

public interface ApplicationPackageService {
    String insertAppPkg(InputApplicationPackage listApp);
    String updateAppPkg(InputApplicationPackage inputAppPackage);
    String deleteAppPkg(List<String> names);
    String searchAppPkg(ApplicationPackageSearchCondition condition);
}
