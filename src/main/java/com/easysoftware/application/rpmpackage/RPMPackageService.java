package com.easysoftware.application.rpmpackage;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface RPMPackageService extends BaseIService<RPMPackageDO> {
    /**
     * Searches for RPM packages.
     *
     * @param condition The search condition.
     * @return ResponseEntity with the search results.
     */
    ResponseEntity<Object> searchRPMPkg(RPMPackageSearchCondition condition);

    /**
     * Queries all RPM package menus.
     *
     * @param condition The search condition.
     * @return Map containing the RPM package menu.
     */
    Map<String, Object> queryAllRPMPkgMenu(RPMPackageSearchCondition condition);

    /**
     * Checks if an application exists.
     *
     * @param name The name of the application.
     * @return true if the application exists, false otherwise.
     */
    boolean existApp(String name);

    /**
     * Queries part of the application package menu.
     *
     * @param condition The search condition.
     * @return List of RPMPackageDomainVo objects representing the queried data.
     */
    List<RPMPackageDomainVo> queryPartAppPkgMenu(RPMPackageSearchCondition condition);

}
