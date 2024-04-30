package com.easysoftware.application.filedapplication;

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
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.fieldapplication.gateway.FieldapplicationGateway;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter.FieldApplicationConverter;

import jakarta.annotation.Resource;

@Service
public class FieldApplicationServiceImpl implements FieldApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(FieldApplicationServiceImpl.class);

    @Autowired
    FieldapplicationGateway domainGateway;

    @Resource
    RPMPackageService rpmService;

    @Resource
    RPMPackageGateway rpmGateway;

    @Resource
    EPKGPackageService epkgService;

    @Resource
    EPKGPackageGateway epkgGateway;

    @Resource
    ApplicationPackageGateway appGateway;

    @Override
    public ResponseEntity<Object> queryMenuByName(FiledApplicationSerachCondition condition) {
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
        } else {
            throw new ParamErrorException("unsupported param: " + name);
        }
    }

    private Map<String, Object> searchDomainMenu(FiledApplicationSerachCondition condition) {
        condition.setName("");
        return domainGateway.queryAll(condition);
    } 

    private Map<String, Object> searchEpkgMenu(FiledApplicationSerachCondition condition) {
        EPKGPackageSearchCondition epkg = FieldApplicationConverter.toEpkg(condition);
        return epkgService.queryAllEPKGPkgMenu(epkg);
    }

    private Map<String, Object> searchRpmMenu(FiledApplicationSerachCondition condition) {
        RPMPackageSearchCondition rpm = FieldApplicationConverter.toRpm(condition);
        return rpmService.queryAllRPMPkgMenu(rpm);
    }

    private Map<String, Object> searchAppMenu(FiledApplicationSerachCondition condtion) {
        ApplicationPackageSearchCondition app = FieldApplicationConverter.toApp(condtion);
        return appGateway.queryMenuByName(app);
    }

    @Override
    public ResponseEntity<Object> searchColumn(FieldColumnSearchCondition condition) {
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

    @Override
    public ResponseEntity<Object> queryDetailByName(FieldDetailSearchCondition condition) {
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

    private EPKGPackageDetailVo searchEpkgDetail(String epkgPkgId) {
        List<EPKGPackageDetailVo> pkgList = epkgGateway.queryDetailByPkgId(epkgPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        logger.error(String.format(MessageCode.EC00014.getMsgEn(), "epkgPkgId"));
        return null;
    }

    private RPMPackageDetailVo searchRpmDetail(String rpmPkgId) {
        List<RPMPackageDetailVo> pkgList = rpmGateway.queryDetailByPkgId(rpmPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        logger.error(String.format(MessageCode.EC00014.getMsgEn(), "rpmPkgId"));
        return null;
    }

    private ApplicationPackageDetailVo searchAppDetail(String appPkgId) {
        List<ApplicationPackageDetailVo> pkgList = appGateway.queryDetailByPkgId(appPkgId);
        if (pkgList.size() >= 1) {
            return pkgList.get(0);
        }
        logger.error(String.format(MessageCode.EC00014.getMsgEn(), "appPkgId"));
        return null;
    }

    @Override
    public ResponseEntity<Object> queryStat() {
        Long rpmNum = rpmGateway.queryTableLength();
        Long appNum = appGateway.queryTableLength();
        Long epkgNum = epkgGateway.queryTableLength();

        Map<String, Long> res = new HashMap<>();
        res.put("apppkg", appNum);
        res.put("total", Math.addExact(rpmNum, epkgNum));
        return ResultUtil.success(HttpStatus.OK, res);
    }
    
}
