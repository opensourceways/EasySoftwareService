/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.application.filedapplication;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.fieldpkg.dto.FieldPkgSearchCondition;
import com.easysoftware.application.filedapplication.dto.FieldColumnSearchCondition;
import com.easysoftware.application.filedapplication.dto.FieldDetailSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.EulerLifeCycleVo;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.application.oepackage.dto.OEPackageSearchCondition;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.common.constant.PackageConstant;
import com.easysoftware.common.constant.RedisConstant;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.archnum.ArchNumGateway;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.eulerlifecycle.gateway.EulerLifeCycleGateway;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;
import com.easysoftware.domain.fieldpkg.gateway.FieldPkgGateway;
import com.easysoftware.domain.oepackage.gateway.OEPackageGateway;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter.FieldApplicationConverter;
import com.easysoftware.ranking.Ranker;
import com.easysoftware.redis.RedisGateway;

import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class FieldApplicationServiceImpl implements FieldApplicationService {
    /**
     * Logger for FieldApplicationServiceImpl.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldApplicationServiceImpl.class);

    /**
     * Domain gateway for FieldApplicationService.
     */
    @Autowired
    private FieldapplicationGateway domainGateway;

    /**
     * RPM Package Service.
     */
    @Resource
    private RPMPackageService rpmService;

    /**
     * RPM Package Gateway.
     */
    @Resource
    private RPMPackageGateway rpmGateway;

    /**
     * EPKG Package Service.
     */
    @Resource
    private EPKGPackageService epkgService;

    /**
     * EPKG Package Gateway.
     */
    @Resource
    private EPKGPackageGateway epkgGateway;

    /**
     * Application Package Gateway.
     */
    @Resource
    private ApplicationPackageGateway appGateway;

    /**
     * FieldPkgGateway Package Gateway.
     */
    @Resource
    private FieldPkgGateway fieldPkgGateway;

    /**
     * OEPkgGateway Package Gateway.
     */
    @Resource
    private OEPackageGateway oePkgGateway;
    /**
     * eulerLifecycleGateway Package Gateway.
     */
    @Resource
    private EulerLifeCycleGateway eulerLifecycleGateway;

    /**
     * Resource injection for the Ranker.
     */
    @Resource
    private Ranker ranker;

    /**
     * gateway.
     */
    @Resource
    private ArchNumGateway archNumGateway;
    /**
     * gateway.
     */
    @Resource
    private RedisGateway redisGateway;

    /**
     * Query menu by name.
     *
     * @param condition The search condition for querying the menu.
     * @return ResponseEntity object containing the query results.
     */
    @Override
    public ResponseEntity<Object> queryMenuByName(final FiledApplicationSerachCondition condition) {
        String name = StringUtils.trimToEmpty(condition.getName());
        if ("apppkg".equals(name)) {
            Map<String, Object> res = searchAppMenu(condition);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("rpmpkg".equals(name)) {
            Map<String, Object> res = searchRpmMenu(condition);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("epkgpkg".equals(name)) {
            Map<String, Object> res = searchEpkgMenu(condition);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("oepkg".equals(name)) {
            Map<String, Object> res = searchOEPkgMenu(condition);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("domain".equals(name)) {
            Map<String, Object> res = searchDomainMenu(condition);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("mainPage".equals(name)) {
            List<Map<String, Object>> cateMap = searchMainPage();
            return ResultUtil.success(HttpStatus.OK, cateMap);
        } else if ("eulerLifecycle".equals(name)) {
            Map<String, Object> lifeCycles = searchLifeCycle();
            return ResultUtil.success(HttpStatus.OK, lifeCycles);
        } else {
            throw new ParamErrorException("the value of parameter name: apppkg, rpmpkg, epkgpkg, all");
        }
    }

    /**
     * Search the euler lifecycle and return a list of maps containing key-value
     * pairs.
     *
     * @return A list of maps with key-value pairs from the euler lifecycle.
     */
    private Map<String, Object> searchLifeCycle() {
        List<EulerLifeCycleVo> elVos = eulerLifecycleGateway.selectAll();
        List<EulerLifeCycleVo> sortVos = SortUtil.sortEulerLifeCycleVo(elVos);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", sortVos.size()),
                Map.entry("list", sortVos));
        return res;
    }

    /**
     * Search the main page and return a list of maps containing key-value pairs.
     *
     * @return A list of maps with key-value pairs from the main page search.
     */
    private List<Map<String, Object>> searchMainPage() {
        Map<String, List<Object>> cateMap = getCategorys();
        List<FiledApplicationVo> fList = domainGateway.queryVoList();
        for (FiledApplicationVo field : fList) {
            String cate = field.getCategory();
            cateMap.get(cate).add(field);
        }

        List<Map<String, Object>> mList = assembleMainPage(cateMap);
        List<Map<String, Object>> rList = ranker.rankingDomainPageByOperationConfig(mList);

        return rList;
    }

    /**
     * Assemble the main page using the provided category map and return a list of
     * maps containing key-value pairs.
     *
     * @param cateMap The category map used for assembling the main page.
     * @return A list of maps with key-value pairs from the assembled main page.
     */
    private List<Map<String, Object>> assembleMainPage(Map<String, List<Object>> cateMap) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (Map.Entry<String, List<Object>> cateEntry : cateMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", cateEntry.getKey());
            map.put("children", cateEntry.getValue());
            res.add(map);
        }
        return res;
    }

    /**
     * Get categories and their associated objects.
     *
     * @return A map containing category names as keys and lists of associated
     *         objects as values.
     */
    private Map<String, List<Object>> getCategorys() {
        Map<String, List<Object>> map = new HashMap<>();
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            String category = categoryEnum.getAlias();
            map.put(category, new ArrayList<>());
        }
        return map;
    }

    /**
     * Search the domain menu based on the specified search condition.
     *
     * @param condition The search condition for filtering domain menu items.
     * @return A map containing domain menu items with their associated objects.
     */
    private Map<String, Object> searchDomainMenu(FiledApplicationSerachCondition condition) {
        FieldPkgSearchCondition con = FieldApplicationConverter.toFieldPkg(condition);
        Map<String, Object> map = fieldPkgGateway.queryMenuByPage(con);

        return map;
    }

    /**
     * Search EPKG menu based on the specified search condition.
     *
     * @param condition The search condition for EPKG menu search.
     * @return A map containing the search results with string keys and object
     *         values.
     */
    private Map<String, Object> searchEpkgMenu(final FiledApplicationSerachCondition condition) {
        EPKGPackageSearchCondition epkg = FieldApplicationConverter.toEpkg(condition);
        Map<String, Object> map = epkgGateway.queryMenuByName(epkg);

        return map;
    }

    /**
     * Search Rpm menu based on the specified search condition.
     *
     * @param condition The search condition for Rpm menu search.
     * @return A map containing the search results with string keys and object
     *         values.
     */
    private Map<String, Object> searchRpmMenu(final FiledApplicationSerachCondition condition) {
        RPMPackageSearchCondition rpm = FieldApplicationConverter.toRpm(condition);
        Map<String, Object> map = rpmGateway.queryMenuByName(rpm);

        return map;
    }

    /**
     * Search OEPkg menu based on the specified search condition.
     *
     * @param condition The search condition for OEPkg menu search.
     * @return A map containing the search results with string keys and object
     *         values.
     */
    private Map<String, Object> searchOEPkgMenu(final FiledApplicationSerachCondition condition) {

        OEPackageSearchCondition oep = FieldApplicationConverter.toOep(condition);
        Map<String, Object> map = oePkgGateway.queryMenuByName(oep);

        return map;
    }

    /**
     * Search Application menu based on the specified search condition.
     *
     * @param condition The search condition for Application menu search.
     * @return A map containing the search results with string keys and object
     *         values.
     */
    private Map<String, Object> searchAppMenu(final FiledApplicationSerachCondition condition) {
        ApplicationPackageSearchCondition app = FieldApplicationConverter.toApp(condition);
        Map<String, Object> map = appGateway.queryMenuByName(app);

        return map;
    }

    /**
     * Search column.
     *
     * @param condition The search condition for column search.
     * @return ResponseEntity object containing the search results.
     */
    @Override
    public ResponseEntity<Object> searchColumn(final FieldColumnSearchCondition condition) {
        List<String> columns = QueryWrapperUtil.splitStr(condition.getColumn());

        String name = StringUtils.trimToEmpty(condition.getName());
        if ("rpmpkg".equals(name)) {
            Map<String, List<String>> res = rpmGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("epkgpkg".equals(name)) {
            Map<String, List<String>> res = epkgGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("apppkg".equals(name)) {
            Map<String, List<String>> res = appGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("domain".equals(name)) {
            Map<String, List<String>> res = fieldPkgGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("oepkg".equals(name)) {
            Map<String, List<String>> res = oePkgGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else {
            throw new ParamErrorException("the value of parameter name: apppkg, rpmpkg, epkgpkg, all");
        }
    }

    /**
     * Query detail by name.
     *
     * @param condition The search condition for querying the detail.
     * @return ResponseEntity object containing the query results.
     */
    @Override
    public ResponseEntity<Object> queryDetailByName(final FieldDetailSearchCondition condition) {
        String appPkgId = StringUtils.trimToEmpty(condition.getAppPkgId());
        String rpmPkgId = StringUtils.trimToEmpty(condition.getRpmPkgId());
        String epkgPkgId = StringUtils.trimToEmpty(condition.getEpkgPkgId());

        Map<String, Object> res = new HashMap<>();
        Set<String> tags = new HashSet<>();

        ApplicationPackageDetailVo app = searchAppDetail(appPkgId);
        if (app != null) {
            res.put("IMAGE", app);
            tags.add("IMAGE");
        }

        RPMPackageDetailVo rpm = searchRpmDetail(rpmPkgId);
        if (rpm != null) {
            res.put("RPM", rpm);
            tags.add("RPM");
        }

        EPKGPackageDetailVo epkg = searchEpkgDetail(epkgPkgId);
        if (epkg != null) {
            res.put("EPKG", epkg);
            tags.add("EPKG");
        }

        List<String> sortedTags = SortUtil.sortTags(tags);
        res.put("tags", sortedTags);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search EPKG package detail by the specified EPKG package ID.
     *
     * @param epkgPkgId The ID of the EPKG package to search for.
     * @return An EPKGPackageDetailVo object containing the details of the EPKG
     *         package.
     */
    private EPKGPackageDetailVo searchEpkgDetail(final String epkgPkgId) {
        if (StringUtils.isBlank(epkgPkgId)) {
            return null;
        }

        List<EPKGPackageDetailVo> pkgList = epkgGateway.queryDetailByPkgId(epkgPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        LOGGER.error(String.format(Locale.ROOT, MessageCode.EC00014.getMsgEn(), "epkgPkgId"));
        return null;
    }

    /**
     * Search RPM package detail by the specified RPM package ID.
     *
     * @param rpmPkgId The ID of the RPM package to search for.
     * @return An RPMPackageDetailVo object containing the details of the RPM
     *         package.
     */
    private RPMPackageDetailVo searchRpmDetail(final String rpmPkgId) {
        if (StringUtils.isBlank(rpmPkgId)) {
            return null;
        }

        List<RPMPackageDetailVo> pkgList = rpmGateway.queryDetailByPkgId(rpmPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        LOGGER.error(String.format(Locale.ROOT, MessageCode.EC00014.getMsgEn(), "rpmPkgId"));
        return null;
    }

    /**
     * Search application package detail by the specified application package ID.
     *
     * @param appPkgId The ID of the application package to search for.
     * @return An ApplicationPackageDetailVo object containing the details of the
     *         application package.
     */
    private ApplicationPackageDetailVo searchAppDetail(final String appPkgId) {
        if (StringUtils.isBlank(appPkgId)) {
            return null;
        }

        List<ApplicationPackageDetailVo> pkgList = appGateway.queryDetailByPkgId(appPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        LOGGER.error(String.format(Locale.ROOT, MessageCode.EC00014.getMsgEn(), "appPkgId"));
        return null;
    }

    /**
     * Query statistics.
     *
     * @return ResponseEntity object containing the statistical information.
     */
    @Override
    public ResponseEntity<Object> queryStat() {
        Long opekgNum;
        Long appNum = appGateway.queryTableLength();
        String opeknumString = redisGateway.get(RedisConstant.DISTINCT_OPEKGNUM);

        if (opeknumString != null) {
            opekgNum = Long.parseLong(opeknumString);
        } else {
            opekgNum = oePkgGateway.queryTableLength();
            if (opekgNum != null && opekgNum > PackageConstant.SOFTWARE_NUM) {
                String opekgNumString = String.valueOf(opekgNum);
                redisGateway.setWithExpire(RedisConstant.DISTINCT_OPEKGNUM, opekgNumString, 90, TimeUnit.MINUTES);
            }
        }

        Map<String, Long> res = new HashMap<>();
        res.put("apppkg", appNum);
        res.put("total", opekgNum);

        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * query pkg num of arch by os.
     *
     * @return pkg nums of arch.
     */
    @Override
    public ResponseEntity<Object> searchArchNumByOs() {
        return ResultUtil.success(HttpStatus.OK, archNumGateway.getAll());
    }

}
