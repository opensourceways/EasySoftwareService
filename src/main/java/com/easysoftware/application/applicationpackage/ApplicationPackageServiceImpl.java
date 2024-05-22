package com.easysoftware.application.applicationpackage;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.obs.ObsService;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {

    /**
     * Resource injection for the ApplicationPackageGateway.
     */
    @Resource
    private ApplicationPackageGateway appPkgGateway;

    /**
     * Autowired service for interacting with Object Storage Service (OBS).
     */
    @Autowired
    private ObsService obsService;

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
    public ResponseEntity<Object> queryPkgByTags(final ApplicationPackageSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryTagsByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Insert a new application package.
     *
     * @param inputAppPkg The input application package to be inserted.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> insertAppPkg(final InputApplicationPackage inputAppPkg) {
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);
        boolean succeed = appPkgGateway.save(appPkg);
        return succeed ? ResultUtil.success(HttpStatus.OK)
                : ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
    }

    /**
     * Update an existing application package.
     *
     * @param inputAppPkg The input application package for updating.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> updateAppPkg(final InputApplicationPackage inputAppPkg) {
        // 数据库中是否已存在该包
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);
        boolean succeed = appPkgGateway.update(appPkg);
        return succeed ? ResultUtil.success(HttpStatus.OK)
                : ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
    }

    /**
     * Delete application packages by their names.
     *
     * @param names List of names of the application packages to be deleted.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> deleteAppPkg(final List<String> names) {
        List<String> existedNames = new ArrayList<>();
        for (String name : names) {
            if (appPkgGateway.existApp(name)) {
                existedNames.add(name);
            }
        }
        List<String> deletedNames = new ArrayList<>();
        for (String name : existedNames) {
            if (appPkgGateway.delete(name)) {
                deletedNames.add(name);
            }
        }

        String msg = String.format(Locale.ROOT, "请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s",
                names, existedNames, deletedNames);
        return ResultUtil.success(HttpStatus.OK);
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
    public ResponseEntity<Object> queryEulerVersionsByName(ApplicationPackageSearchCondition condition) {
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
    public ResponseEntity<Object> queryEulerArchsByName(ApplicationPackageSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryEulerArchsByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
