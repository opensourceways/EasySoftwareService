package com.easysoftware.application.rpmpackage;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RPMPackageService extends BaseIService<RPMPackageDO> {
    /**
     * Inserts an RPM package.
     *
     * @param inputrPMPackage The input RPM package.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> insertRPMPkg(InputRPMPackage inputrPMPackage);

    /**
     * Updates an RPM package.
     *
     * @param inputrPMPackage The input RPM package.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> updateRPMPkg(InputRPMPackage inputrPMPackage);

    /**
     * Deletes RPM packages.
     *
     * @param names List of names of RPM packages to delete.
     * @return ResponseEntity with the result of the operation.
     */
    ResponseEntity<Object> deleteRPMPkg(List<String> names);

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
     * Saves a data object.
     *
     * @param dataObject The data object to save.
     */
    void saveDataObject(String dataObject);

    /**
     * Saves a batch of data objects.
     *
     * @param dataObject List of data objects to save.
     */
    void saveDataObjectBatch(ArrayList<String> dataObject);

    /**
     * Queries part of the application package menu.
     *
     * @param condition The search condition.
     * @return List of RPMPackageDomainVo objects representing the queried data.
     */
    List<RPMPackageDomainVo> queryPartAppPkgMenu(RPMPackageSearchCondition condition);

}
