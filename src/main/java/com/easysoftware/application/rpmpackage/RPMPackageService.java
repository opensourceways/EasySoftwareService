package com.easysoftware.application.rpmpackage;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;

public interface RPMPackageService {
    ResponseEntity<Object> insertRPMPkg(InputRPMPackage inputrPMPackage);
    ResponseEntity<Object> updateRPMPkg(InputRPMPackage inputrPMPackage);
    ResponseEntity<Object> deleteRPMPkg(List<String> names);
    ResponseEntity<Object> searchRPMPkg(RPMPackageSearchCondition condition);
    Map<String, Object> queryAllRPMPkgMenu(RPMPackageSearchCondition condition);
}
