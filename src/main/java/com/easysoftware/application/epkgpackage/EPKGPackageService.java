package com.easysoftware.application.epkgpackage;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;

public interface EPKGPackageService {
    ResponseEntity<Object> insertEPKGPkg(InputEPKGPackage inputrPMPackage);
    ResponseEntity<Object> updateEPKGPkg(InputEPKGPackage inputrPMPackage);
    ResponseEntity<Object> deleteEPKGPkg(List<String> names);
    ResponseEntity<Object> searchEPKGPkg(EPKGPackageSearchCondition condition);
    Map<String, Object> queryAllEPKGPkgMenu(EPKGPackageSearchCondition condition);
}
