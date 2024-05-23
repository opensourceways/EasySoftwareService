package com.easysoftware.domain.epkgpackage.gateway;

import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EPKGPackageGateway {
    /**
     * Check if an EPKG package exists based on its unique identifier.
     *
     * @param unique The unique identifier of the EPKG package
     * @return true if the EPKG package exists, false otherwise
     */
    boolean existEPKG(EPKGPackageUnique unique);

    /**
     * Check if an EPKG package exists based on its ID.
     *
     * @param id The ID of the EPKG package
     * @return true if the EPKG package exists, false otherwise
     */
    boolean existEPKG(String id);

    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying EPKG package details
     * @return A map containing detailed information
     */
    Map<String, Object> queryDetailByName(EPKGPackageSearchCondition condition);

    /**
     * Query detailed information by package ID.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of EPKGPackageDetailVo objects
     */
    List<EPKGPackageDetailVo> queryDetailByPkgId(String pkgId);

    /**
     * Query menu items based on the provided search condition.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    Map<String, Object> queryMenuByName(EPKGPackageSearchCondition condition);

    /**
     * Query columns based on the provided list of columns.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    Map<String, List<String>> queryColumn(List<String> columns);

    /**
     * Get the total number of records in the table.
     *
     * @return The total number of records in the table
     */
    long queryTableLength();

    /**
     * Select a single EPKGPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected EPKGPackageMenuVo object
     */
    EPKGPackageMenuVo selectOne(String name);

    /**
     * Query Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerVersionByName(EPKGPackageSearchCondition condition);

    /**
     * Query Euler archs based on the provided search condition.
     *
     * @param condition The search condition for querying EulerVersion
     * @return A map containing tags information
     */
    Map<String, Object> queryEulerArchsByName(EPKGPackageSearchCondition condition);
}
