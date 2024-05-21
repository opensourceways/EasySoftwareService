package com.easysoftware.domain.rpmpackage.gateway;

import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RPMPackageGateway {
    /**
     * Check if an RPM package exists based on its unique identifier.
     *
     * @param unique The unique identifier of the RPM package
     * @return true if the RPM package exists, false otherwise
     */
    boolean existRPM(RPMPackageUnique unique);

    /**
     * Check if an RPM package exists based on its ID.
     *
     * @param id The ID of the RPM package
     * @return true if the RPM package exists, false otherwise
     */
    boolean existRPM(String id);

    /**
     * Save an RPMPackage object.
     *
     * @param appPkg The RPMPackage object to save
     * @return true if the save operation was successful, false otherwise
     */
    boolean save(RPMPackage appPkg);

    /**
     * Update an existing RPMPackage object.
     *
     * @param appPkg The RPMPackage object to update
     * @return the number of rows affected by the update operation
     */
    int update(RPMPackage appPkg);

    /**
     * Delete RPM packages by their IDs.
     *
     * @param id A list of IDs of RPM packages to delete
     * @return the number of rows deleted
     */
    int delete(List<String> id);

    /**
     * Query detailed information based on the provided search condition for RPM
     * packages.
     *
     * @param condition The search condition for querying RPM package details
     * @return A map containing detailed information
     */
    Map<String, Object> queryDetailByName(RPMPackageSearchCondition condition);

    /**
     * Query detailed information by package ID for RPM packages.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of RPMPackageDetailVo objects
     */
    List<RPMPackageDetailVo> queryDetailByPkgId(String pkgId);

    /**
     * Query menu items based on the provided search condition for RPM packages.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    Map<String, Object> queryMenuByName(RPMPackageSearchCondition condition);

    /**
     * Query columns based on the provided list of columns for RPM packages.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    Map<String, List<String>> queryColumn(List<String> columns);

    /**
     * Get the total number of records in the RPM package table.
     *
     * @return The total number of records in the table
     */
    long queryTableLength();

    /**
     * Convert a batch of data objects to RPMPackageDO objects.
     *
     * @param dataObject A collection of data objects to convert
     * @return A collection of RPMPackageDO objects
     */
    Collection<RPMPackageDO> convertBatch(Collection<String> dataObject);

    /**
     * Query part of the RPM package menu based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the RPM package
     *                  menu
     * @return A map containing relevant information
     */
    Map<String, Object> queryPartRPMPkgMenu(RPMPackageSearchCondition condition);

    /**
     * Select a single RPMPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected RPMPackageMenuVo object
     */
    RPMPackageMenuVo selectOne(String name);

    /**
     * Query Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerVersionByName(RPMPackageSearchCondition condition);

    /**
     * Query Euler archs based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerArchsByName(RPMPackageSearchCondition condition);

}
