package com.easysoftware.application.domainpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.domainpackage.converter.DomainPackageConverter;
import com.easysoftware.application.domainpackage.dto.DomainColumnCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;
import com.easysoftware.application.domainpackage.vo.DomainPackageMenuVo;
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;

import jakarta.annotation.Resource;

@Service
public class DomainPackageServiceImpl implements DomainPackageService {
    @Resource
    ApplicationPackageService appPkgService;

    @Resource
    RPMPackageService rPMPkgService;

    @Resource
    EPKGPackageService epkgPackageService;

    @Resource
    RPMPackageGateway rpmPackageGateway;

    @Resource
    EPKGPackageGateway epkgPackageGateway;

    @Resource
    ApplicationPackageGateway applicationPackageGateway;

    @Override
    public ResponseEntity<Object> searchDomain(DomainSearchCondition condition) {
        String name = condition.getName();
        String entity = condition.getEntity();

        // 搜索domain页面多个软件包
        if (StringUtils.isBlank(entity) && StringUtils.isNotBlank(name)) {
            return searchDomainPage(condition);
        // 搜索domain页面单个软件包
        } else if (StringUtils.isBlank(name) && StringUtils.isNotBlank(entity)) {
            return searchDomainEntity(condition);
        } else {
            return null;
        }
    }

    private ResponseEntity<Object> searchDomainEntity(DomainSearchCondition conditon) {
        String entity = conditon.getEntity();
        DomainPackageMenuVo domain = new DomainPackageMenuVo();
        domain.setName(entity);

        domain = extendIds(domain);
        
        Map res = Map.ofEntries(
            Map.entry("total", "-1"),
            Map.entry("list", domain)
        );
        return ResultUtil.success(HttpStatus.OK, res);
    }

    private ResponseEntity<Object> searchDomainPage(DomainSearchCondition condition) {
        // 展示精品应用
        if ("apppkg".equals(condition.getName())) {
            ApplicationPackageSearchCondition appCon = DomainPackageConverter.toApp(condition);
            Map<String, Object> res = appPkgService.queryAllAppPkgMenu(appCon);
            return ResultUtil.success(HttpStatus.OK, res);
        // 展示rpm软件包
        } else if ("rpmpkg".equals(condition.getName())) {
            RPMPackageSearchCondition rpmCon = DomainPackageConverter.toRpm(condition);
            Map<String, Object> rpmMenuList = rPMPkgService.queryAllRPMPkgMenu(rpmCon);
            return ResultUtil.success(HttpStatus.OK, rpmMenuList);
        // 展示epkg软件包
        } else if ("epkgpkg".equals(condition.getName())) {
            EPKGPackageSearchCondition epkgCon = DomainPackageConverter.toEpkg(condition);
            Map<String, Object> epkgMenu = epkgPackageService.queryAllEPKGPkgMenu(epkgCon);
            return ResultUtil.success(HttpStatus.OK, epkgMenu);
        // 展示领域应用
        } else if ("all".equals(condition.getName())) {
            ResponseEntity<Object> res = searchAllEntity(condition);
            return res;
        } else {
            return null;
        }
    }


    private ResponseEntity<Object> searchAllEntity(DomainSearchCondition condition) {
        ApplicationPackageSearchCondition appCon = DomainPackageConverter.toApp(condition);
        appCon.setPageSize(Integer.MAX_VALUE);
        List<ApplicationPackageMenuVo> appMenus = appPkgService.queryPkgMenuList(appCon);

        RPMPackageSearchCondition rpmCon = DomainPackageConverter.toRpm(condition);
        rpmCon.setPageSize(Integer.MAX_VALUE);
        rpmCon.setTimeOrder("");
        List<RPMPackageDomainVo> rpmMenus = rPMPkgService.queryPartAppPkgMenu(rpmCon);

        List<DomainPackageMenuVo> domainMenus = mergeMenuVOs(appMenus, rpmMenus);
        for (DomainPackageMenuVo menu : domainMenus) {
            menu = extendIds(menu);
        }

        List<Map<String, Object>> appCate = groupDomainByCategory(domainMenus);
        Map res = Map.ofEntries(
            Map.entry("total", domainMenus.size()),
            Map.entry("list", appCate)
        );
        return ResultUtil.success(HttpStatus.OK, res);
    }

    private DomainPackageMenuVo extendIds(DomainPackageMenuVo domain) {
        String name = domain.getName();
        Set<String> tags = domain.getTags();
        if (!tags.contains("IMAGE")) {
            ApplicationPackageMenuVo app = applicationPackageGateway.selectOne(name);
            if (StringUtils.isNotBlank(app.getPkgId())) {
                tags.add("IMAGE");
                domain.getPkgIds().put("IMAGE", app.getPkgId());
            }
        }
        if (!tags.contains("RPM")) {
            RPMPackageMenuVo rpm = rpmPackageGateway.selectOne(name);
            if (StringUtils.isNotBlank(rpm.getPkgId())) {
                tags.add("RPM");
                domain.getPkgIds().put("RPM", rpm.getPkgId());
            }
        }
        if (!tags.contains("EPKG")) {
            EPKGPackageMenuVo epkg = epkgPackageGateway.selectOne(name);
            if (StringUtils.isNotBlank(epkg.getPkgId())) {
                tags.add("EPKG");
                domain.getPkgIds().put("EPKG", epkg.getPkgId());
            }
        }
        return domain;
    }

    private List<DomainPackageMenuVo> mergeMenuVOs(List<ApplicationPackageMenuVo> appMenus,
            List<RPMPackageDomainVo> rpmMenus) {
        Map<String, DomainPackageMenuVo> domainMap = new HashMap<>();
        for (ApplicationPackageMenuVo app: appMenus) {
            DomainPackageMenuVo domain = new DomainPackageMenuVo();
            BeanUtils.copyProperties(app, domain);
            domain.getTags().add("IMAGE");
            domain.getPkgIds().put("IMAGE", app.getPkgId());
            domainMap.put(app.getName(), domain);
        }

        for (RPMPackageDomainVo rpm: rpmMenus) {
            String name = rpm.getName();
            if (domainMap.containsKey(name)) {
                domainMap.get(name).getTags().add("RPM");
                continue;
            }

            DomainPackageMenuVo domain = new DomainPackageMenuVo();
            BeanUtils.copyProperties(rpm, domain);
            domain.getTags().add("RPM");
            domain.getPkgIds().put("RPM", rpm.getPkgId());
            domainMap.put(rpm.getName(), domain);
        }

        return new ArrayList<DomainPackageMenuVo>(domainMap.values());
    }


    private List<Map<String, Object>> groupDomainByCategory(Collection<DomainPackageMenuVo> domainCollection) {
        Map<String, List<DomainPackageMenuVo>> map = new HashMap<>();
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            String category = categoryEnum.getAlias();
            map.put(category, new ArrayList<>());
        }

        for (DomainPackageMenuVo domain : domainCollection) {
            String cate = StringUtils.trimToEmpty(domain.getCategory());
            map.get(cate).add(domain);
        }
    
        List<Map<String, Object>> res = new ArrayList<>();
        for (String category: map.keySet()) {
            Map<String, Object> cMap = new HashMap<>();
            cMap.put("name", category);
            cMap.put("children", map.get(category));
            res.add(cMap);
        }
    
        return res;
    }

    private List<Map<String, Object>> groupByCategory(List<ApplicationPackageMenuVo> menuList) {
        Map<String, List<ApplicationPackageMenuVo>> map = new HashMap<>();
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            String category = categoryEnum.getAlias();
            map.put(category, new ArrayList<>());
        }
    
        for (ApplicationPackageMenuVo menu: menuList) {
            map.get(menu.getCategory()).add(menu);
        }
    
        List<Map<String, Object>> res = new ArrayList<>();
        for (String category: map.keySet()) {
            Map<String, Object> cMap = new HashMap<>();
            cMap.put("name", category);
            cMap.put("children", map.get(category));
            res.add(cMap);
        }
    
        return res;
    }

    @Override
    public ResponseEntity<Object> searchColumn(DomainColumnCondition condition) {
        if ("rpmpkg".equals(condition.getName())) {
            List<String> res = rpmPackageGateway.queryColumn(condition.getColumn());
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("epkgpkg".equals(condition.getName())) {
            List<String> res = epkgPackageGateway.queryColumn(condition.getColumn());
            return ResultUtil.success(HttpStatus.OK, res);
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<Object> queryStat() {
        Long rpmNum = rpmPackageGateway.queryTableLength();
        Long appNum = applicationPackageGateway.queryTableLength();
        Long epkgNum = epkgPackageGateway.queryTableLength();

        Map<String, Long> res = Map.ofEntries(
            Map.entry("apppkg", appNum),
            Map.entry("total", rpmNum + epkgNum)
        );
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
