package com.easysoftware.application.applicationpackage;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageNameSearchCondition;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {

    /**
     * Resource injection for the ApplicationPackageGateway.
     */
    @Resource
    private ApplicationPackageGateway appPkgGateway;

    /**
     * Value injection for the repoMaintainerApi configuration property.
     */
    @Value("${api.repoMaintainer}")
    private String repoMaintainerApi;

    /**
     * Value injection for the repoInfoApi configuration property.
     */
    @Value("${api.repoInfo}")
    private String repoInfoApi;

    /**
     * Value injection for the repoDownloadApi configuration property.
     */
    @Value("${api.repoDownload}")
    private String repoDownloadApi;

    /**
     * Value injection for the repoSigApi configuration property.
     */
    @Value("${api.repoSig}")
    private String repoSigApi;

    /**
     * Query application packages based on specified tags.
     *
     * @param condition The search condition containing tags for querying
     *                  application packages.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> queryPkgByTags(final ApplicationPackageNameSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryTagsByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search for application packages based on the provided search condition.
     *
     * @param condition The search condition for querying application packages.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchAppPkg(final ApplicationPackageSearchCondition condition) {
        condition.setTimeOrder("");
        Map<String, Object> res = appPkgGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Query a list of application package menus based on the provided search
     * condition.
     *
     * @param condition The search condition for querying application package menus.
     * @return List of ApplicationPackageMenuVo representing the menu list.
     */
    @Override
    public List<ApplicationPackageMenuVo> queryPkgMenuList(final ApplicationPackageSearchCondition condition) {
        Map<String, Object> map = appPkgGateway.queryMenuByName(condition);
        return (List<ApplicationPackageMenuVo>) map.get("list");
    }

    /**
     * Queries all available openEuler version of application package.
     *
     * @param condition The search condition.
     * @return Map containing the epkg package menu.
     */
    @Override
    public ResponseEntity<Object> queryEulerVersionsByName(ApplicationPackageNameSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryEulerVersionByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Queries all available openEuler archs of application package.
     *
     * @param condition The search condition.
     * @return Map containing the epkg package menu.
     */
    @Override
    public ResponseEntity<Object> queryEulerArchsByName(ApplicationPackageNameSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryEulerArchsByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
