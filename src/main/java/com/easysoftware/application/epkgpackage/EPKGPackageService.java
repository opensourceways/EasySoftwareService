package com.easysoftware.application.epkgpackage;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageNameSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface EPKGPackageService extends BaseIService<EPKGPackageDO> {
    /**
     * Searches for EPKG packages based on search conditions.
     *
     * @param condition EPKGPackageSearchCondition object.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchEPKGPkg(EPKGPackageSearchCondition condition);

    /**
     * Queries all EPKG package menus based on search conditions.
     *
     * @param condition EPKGPackageSearchCondition object.
     * @return Map containing the menu data.
     */
    Map<String, Object> queryAllEPKGPkgMenu(EPKGPackageSearchCondition condition);

    /**
     * Queries available openEuler version of epkg package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerVersionsByName(EPKGPackageNameSearchCondition condition);
}
