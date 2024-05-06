package com.easysoftware.application.filedapplication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.filedapplication.dto.FieldColumnSearchCondition;
import com.easysoftware.application.filedapplication.dto.FieldDetailSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter.FieldApplicationConverter;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        } else if ("domain".equals(name)) {
            Map<String, Object> res = searchDomainMenu(condition);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("mainPage".equals(name)) {
            List<Map<String, Object>> cateMap = searchMainPage();
            return ResultUtil.success(HttpStatus.OK, cateMap);
        } else {
            throw new ParamErrorException("unsupported param: " + name);
        }
    }


    private List<Map<String, Object>> searchMainPage() {
        Map<String, List<Object>> cateMap = getCategorys();
        List<FiledApplicationVo> fList = domainGateway.queryVoList();
        for (FiledApplicationVo field : fList) {
            String cate = field.getCategory();
            cateMap.get(cate).add(field);
        }
        return assembleMainPage(cateMap);
    }

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

    private Map<String, List<Object>> getCategorys() {
        Map<String, List<Object>> map = new HashMap<>();
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            String category = categoryEnum.getAlias();
            map.put(category, new ArrayList<>());
        }
        return map;
    }

    private Map<String, Object> searchDomainMenu(FiledApplicationSerachCondition condition) {
        condition.setName("");
        return domainGateway.queryMenuByPage(condition);
    } 


    /**
     * Search EPKG menu based on the specified search condition.
     *
     * @param condition The search condition for EPKG menu search.
     * @return A map containing the search results with string keys and object values.
     */
    private Map<String, Object> searchEpkgMenu(final FiledApplicationSerachCondition condition) {
        EPKGPackageSearchCondition epkg = FieldApplicationConverter.toEpkg(condition);
        return epkgService.queryAllEPKGPkgMenu(epkg);
    }


    /**
     * Search Rpm menu based on the specified search condition.
     *
     * @param condition The search condition for Rpm menu search.
     * @return A map containing the search results with string keys and object values.
     */
    private Map<String, Object> searchRpmMenu(final FiledApplicationSerachCondition condition) {
        RPMPackageSearchCondition rpm = FieldApplicationConverter.toRpm(condition);
        return rpmService.queryAllRPMPkgMenu(rpm);
    }


    /**
     * Search Application menu based on the specified search condition.
     *
     * @param condition The search condition for Application menu search.
     * @return A map containing the search results with string keys and object values.
     */
    private Map<String, Object> searchAppMenu(final FiledApplicationSerachCondition condition) {
        ApplicationPackageSearchCondition app = FieldApplicationConverter.toApp(condition);
        return appGateway.queryMenuByName(app);
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
            Map<String, List<String>> res = domainGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else {
            throw new ParamErrorException("unsupported param: " + condition.getName());
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

        res.put("tags", tags);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search EPKG package detail by the specified EPKG package ID.
     *
     * @param epkgPkgId The ID of the EPKG package to search for.
     * @return An EPKGPackageDetailVo object containing the details of the EPKG package.
     */
    private EPKGPackageDetailVo searchEpkgDetail(final String epkgPkgId) {
        List<EPKGPackageDetailVo> pkgList = epkgGateway.queryDetailByPkgId(epkgPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        LOGGER.error(String.format(MessageCode.EC00014.getMsgEn(), "epkgPkgId"));
        return null;
    }

    /**
     * Search RPM package detail by the specified RPM package ID.
     *
     * @param rpmPkgId The ID of the RPM package to search for.
     * @return An RPMPackageDetailVo object containing the details of the RPM package.
     */
    private RPMPackageDetailVo searchRpmDetail(final String rpmPkgId) {
        List<RPMPackageDetailVo> pkgList = rpmGateway.queryDetailByPkgId(rpmPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        LOGGER.error(String.format(MessageCode.EC00014.getMsgEn(), "rpmPkgId"));
        return null;
    }

    /**
     * Search application package detail by the specified application package ID.
     *
     * @param appPkgId The ID of the application package to search for.
     * @return An ApplicationPackageDetailVo object containing the details of the application package.
     */
    private ApplicationPackageDetailVo searchAppDetail(final String appPkgId) {
        List<ApplicationPackageDetailVo> pkgList = appGateway.queryDetailByPkgId(appPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        LOGGER.error(String.format(MessageCode.EC00014.getMsgEn(), "appPkgId"));
        return null;
    }


    /**
     * Query statistics.
     *
     * @return ResponseEntity object containing the statistical information.
     */
    @Override
    public ResponseEntity<Object> queryStat() {
        long rpmNum = rpmGateway.queryTableLength();
        Long appNum = appGateway.queryTableLength();
        long epkgNum = epkgGateway.queryTableLength();

        Map<String, Long> res = new HashMap<>();
        res.put("apppkg", appNum);
        res.put("total", Math.addExact(rpmNum, epkgNum));
        return ResultUtil.success(HttpStatus.OK, res);
    }

}
