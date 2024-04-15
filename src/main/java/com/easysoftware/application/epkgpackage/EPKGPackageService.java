package com.easysoftware.application.epkgpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;

public interface EPKGPackageService extends BaseIService<EPKGPackageDO> {
    ResponseEntity<Object> insertEPKGPkg(InputEPKGPackage inputrPMPackage);
    ResponseEntity<Object> updateEPKGPkg(InputEPKGPackage inputrPMPackage);
    ResponseEntity<Object> deleteEPKGPkg(List<String> names);
    ResponseEntity<Object> searchEPKGPkg(EPKGPackageSearchCondition condition);
    Map<String, Object> queryAllEPKGPkgMenu(EPKGPackageSearchCondition condition);
    boolean existApp(String name);
    void saveDataObject(String dataObject);
    void saveDataObjectBatch(ArrayList<String> dataObject);
}
