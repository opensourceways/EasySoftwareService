package com.easysoftware.domain.applicationpackage.gateway;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;

import java.util.List;
import java.util.Map;

public interface ApplicationPackageGateway {
    /**
     * Check if an application exists based on its name.
     *
     * @param name The name of the application
     * @return true if the application exists, false otherwise
     */
    boolean existApp(String name);

    /**
     * Save an ApplicationPackage object.
     *
     * @param appPkg The ApplicationPackage object to save
     * @return true if the save operation was successful, false otherwise
     */
    boolean save(ApplicationPackage appPkg);

    /**
     * Update an existing ApplicationPackage object.
     *
     * @param appPkg The ApplicationPackage object to update
     * @return true if the update operation was successful, false otherwise
     */
    boolean update(ApplicationPackage appPkg);

    /**
     * Delete an application by name.
     *
     * @param name The name of the application to delete
     * @return true if the delete operation was successful, false otherwise
     */
    boolean delete(String name);

    /**
     * Query menu items based on the provided search condition.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    Map<String, Object> queryMenuByName(ApplicationPackageSearchCondition condition);

    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying detailed information
     * @return A map containing detailed information
     */
    Map<String, Object> queryDetailByName(ApplicationPackageSearchCondition condition);

    /**
     * Query detailed information by package ID.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of ApplicationPackageDetailVo objects
     */
    List<ApplicationPackageDetailVo> queryDetailByPkgId(String pkgId);

    /**
     * Get the total number of records in the table.
     *
     * @return The total number of records in the table
     */
    long queryTableLength();

    /**
     * Select a single ApplicationPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected ApplicationPackageMenuVo object
     */
    ApplicationPackageMenuVo selectOne(String name);

    /**
     * Query columns based on the provided list of columns.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    Map<String, List<String>> queryColumn(List<String> columns);

    /**
     * Query tags based on the provided search condition.
     *
     * @param condition The search condition for querying tags
     * @return A map containing tags information
     */
    Map<String, Object> queryTagsByName(ApplicationPackageSearchCondition condition);

}
