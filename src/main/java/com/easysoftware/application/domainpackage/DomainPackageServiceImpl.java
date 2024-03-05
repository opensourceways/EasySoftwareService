package com.easysoftware.application.domainpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;
import com.easysoftware.application.domainpackage.vo.DomainPackageMenuVo;
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

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

    @Override
    public ResponseEntity<Object> searchDomain(DomainSearchCondition condition) {
        String name = condition.getName();
        String entity = condition.getEntity();

        // 搜索domain页面 
        if (StringUtils.isBlank(entity) && StringUtils.isNotBlank(name)) {
            return searchDomainPage(condition);
        // 搜索单个domain包
        } else if (StringUtils.isBlank(name) && StringUtils.isNotBlank(entity)) {
            return searchDomainEntity(condition);
        } else {
            return null;
        }
    }

    private ResponseEntity<Object> searchDomainEntity(DomainSearchCondition conditon) {
        String entity = conditon.getEntity();
        DomainPackageMenuVo domain = new DomainPackageMenuVo();
        domain.setTags(new ArrayList<String>());
        domain.getTags().add("image");

        RPMPackageUnique unique = new RPMPackageUnique();
        unique.setName(entity);
        boolean rpmExisted = rpmPackageGateway.existRPM(unique);
        if (rpmExisted) {
            domain.getTags().add("RPM");
        }

        EPKGPackageUnique epkg = new EPKGPackageUnique();
        epkg.setName(entity);
        boolean epkgExisted = epkgPackageGateway.existEPKG(epkg);
        if (epkgExisted) {
            domain.getTags().add("EPKG");
        }

        Map res = Map.ofEntries(
            Map.entry("total", "-1"),
            Map.entry("list", domain)
        );
        return ResultUtil.success(HttpStatus.OK, res);
    }

    private ResponseEntity<Object> searchDomainPage(DomainSearchCondition condition) {
        if ("apppkg".equals(condition.getName())) {
            ApplicationPackageSearchCondition appCon = new ApplicationPackageSearchCondition();
            BeanUtils.copyProperties(condition, appCon);
            Map<String, Object> map = appPkgService.queryAllAppPkgMenu(appCon);
            List<ApplicationPackageMenuVo> appMenus = (List<ApplicationPackageMenuVo>) map.get("list");
            
            List<Map<String, Object>> appCate = groupByCategory(appMenus);
            Map res = Map.ofEntries(
                Map.entry("total", map.get("total")),
                Map.entry("list", appCate)
            );
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("rpmpkg".equals(condition.getName())) {
            RPMPackageSearchCondition rPMCon = new RPMPackageSearchCondition();
            BeanUtils.copyProperties(condition, rPMCon);
            rPMCon.setName("");
            rPMCon.setRpmCategory(condition.getCategory());
            Map<String, Object> rpmMenuList = rPMPkgService.queryAllRPMPkgMenu(rPMCon);
            return ResultUtil.success(HttpStatus.OK, rpmMenuList);
        } else if ("epkgpkg".equals(condition.getName())) {
            EPKGPackageSearchCondition eCon = new EPKGPackageSearchCondition();
            BeanUtils.copyProperties(condition, eCon);
            eCon.setName("");
            eCon.setEpkgCategory(condition.getCategory());
            Map<String, Object> epkgMenu = epkgPackageService.queryAllEPKGPkgMenu(eCon);
            return ResultUtil.success(HttpStatus.OK, epkgMenu);
        } else if ("all".equals(condition.getName())) {
            ResponseEntity<Object> res = searchAllEntity(condition);
            return res;
        } else {
            return null;
        }
    }

    private ResponseEntity<Object> searchAllEntity(DomainSearchCondition condition) {
        ApplicationPackageSearchCondition appCon = new ApplicationPackageSearchCondition();
        BeanUtils.copyProperties(condition, appCon);
        Map<String, Object> map = appPkgService.queryAllAppPkgMenu(appCon);
        List<ApplicationPackageMenuVo> appMenus = (List<ApplicationPackageMenuVo>) map.get("list");

        List<DomainPackageMenuVo> domainMenus = new ArrayList<>();
        for (ApplicationPackageMenuVo app: appMenus) {
            DomainPackageMenuVo domain = new DomainPackageMenuVo();
            BeanUtils.copyProperties(app, domain);
            domain.setTags(new ArrayList<String>());
            domain.getTags().add("image");

            String name = domain.getName();

            // search RPM
            RPMPackageUnique unique = new RPMPackageUnique();
            unique.setName(name);
            boolean rpmExisted = rpmPackageGateway.existRPM(unique);
            if (rpmExisted) {
                domain.getTags().add("RPM");
            }

            // search RPM
            EPKGPackageUnique epkg = new EPKGPackageUnique();
            epkg.setName(name);
            boolean epkgExisted = epkgPackageGateway.existEPKG(epkg);
            if (epkgExisted) {
                domain.getTags().add("EPKG");
            }

            domainMenus.add(domain);

        }
       
        List<Map<String, Object>> appCate = groupDomainByCategory(domainMenus);
        Map res = Map.ofEntries(
            Map.entry("total", map.get("total")),
            Map.entry("list", appCate)
        );
        return ResultUtil.success(HttpStatus.OK, res);

    }


    private List<Map<String, Object>> groupDomainByCategory(List<DomainPackageMenuVo> menuList) {
        Map<String, List<DomainPackageMenuVo>> map = new HashMap<>();
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            String category = categoryEnum.getAlias();
            map.put(category, new ArrayList<>());
        }
    
        for (DomainPackageMenuVo menu: menuList) {
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
}
