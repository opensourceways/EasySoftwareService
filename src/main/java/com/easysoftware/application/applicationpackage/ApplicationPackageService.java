package com.easysoftware.application.applicationpackage;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicationPackageService {
    /**
     * Insert a new application package.
     *
     * @param listApp The input application package to be inserted.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> insertAppPkg(InputApplicationPackage listApp);

    /**
     * Update an existing application package.
     *
     * @param inputAppPackage The input application package for updating.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> updateAppPkg(InputApplicationPackage inputAppPackage);

    /**
     * Delete application packages by their names.
     *
     * @param names List of names of the application packages to be deleted.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> deleteAppPkg(List<String> names);

    /**
     * Search for application packages based on the provided search condition.
     *
     * @param condition The search condition for querying application packages.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> searchAppPkg(ApplicationPackageSearchCondition condition);

    /**
     * Query a list of application package menus based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application package menus.
     * @return List of ApplicationPackageMenuVo representing the menu list.
     */
    List<ApplicationPackageMenuVo> queryPkgMenuList(ApplicationPackageSearchCondition condition);

    /**
     * Query application packages based on specified tags.
     *
     * @param condition The search condition containing tags for querying
     *                  application packages.
     * @return ResponseEntity<Object>.
     */
    ResponseEntity<Object> queryPkgByTags(ApplicationPackageSearchCondition condition);

    /**
     * Queries available openEuler version of application package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerVersionsByName(ApplicationPackageSearchCondition condition);

    /**
     * Queries available openEuler archs of application package.
     *
     * @param condition The search condition.
     * @return Map containing the openEuler versions.
     */
    ResponseEntity<Object> queryEulerArchsByName(ApplicationPackageSearchCondition condition);
}
