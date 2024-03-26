package com.easysoftware.application.rpmpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

public interface RPMPackageService extends BaseIService<RPMPackageDO> {
    ResponseEntity<Object> insertRPMPkg(InputRPMPackage inputrPMPackage);
    ResponseEntity<Object> updateRPMPkg(InputRPMPackage inputrPMPackage);
    ResponseEntity<Object> deleteRPMPkg(List<String> names);
    ResponseEntity<Object> searchRPMPkg(RPMPackageSearchCondition condition);
    Map<String, Object> queryAllRPMPkgMenu(RPMPackageSearchCondition condition);
    boolean existApp(String name);
    void saveDataObject(String dataObject);
    void saveDataObjectBatch(ArrayList<String> dataObject);
	List<RPMPackageDomainVo> queryPartAppPkgMenu(RPMPackageSearchCondition condition);
    
}
