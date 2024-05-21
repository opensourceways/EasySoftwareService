package com.easysoftware.application.epkgpackage;

import com.easysoftware.application.BaseIService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface EPKGPackageService extends BaseIService<EPKGPackageDO> {
    /**
     * Inserts an EPKG package.
     *
     * @param inputrPMPackage InputEPKGPackage object.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> insertEPKGPkg(InputEPKGPackage inputrPMPackage);

    /**
     * Updates an EPKG package.
     *
     * @param inputrPMPackage InputEPKGPackage object.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> updateEPKGPkg(InputEPKGPackage inputrPMPackage);

    /**
     * Deletes EPKG packages by their names.
     *
     * @param names List of package names to delete.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> deleteEPKGPkg(List<String> names);

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
     * Checks if an application with a given name exists.
     *
     * @param name Name of the application.
     * @return boolean indicating if the application exists.
     */
    boolean existApp(String name);

    /**
     * Saves a single data object.
     *
     * @param dataObject Data object to save.
     */
    void saveDataObject(String dataObject);

    /**
     * Saves a batch of data objects.
     *
     * @param dataObject ArrayList of data objects to save.
     */
    void saveDataObjectBatch(ArrayList<String> dataObject);

    /**
     * Queries available openEuler version of epkg package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerVersionsByName(EPKGPackageSearchCondition condition);

    /**
     * Queries available openEuler archs of epkg package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerArchsByName(EPKGPackageSearchCondition condition);

}
