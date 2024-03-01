package com.easysoftware.application.domainpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.ResultUtil;
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

    @Override
    public ResponseEntity<Object> searchDomain(DomainSearchCondition condition) {
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
            Map<String, Object> rpmMenuList = rPMPkgService.queryAllRPMPkgMenu(rPMCon);
            return ResultUtil.success(HttpStatus.OK, rpmMenuList);
        } else if ("epkgpkg".equals(condition.getName())) {
            EPKGPackageSearchCondition eCon = new EPKGPackageSearchCondition();
            BeanUtils.copyProperties(condition, eCon);
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
            domain.setTags(new ArrayList<>(app.getTags()));

            String name = domain.getName();

            // search RPM
            RPMPackageUnique unique = new RPMPackageUnique();
            unique.setName(name);
            boolean rpmExisted = rpmPackageGateway.existRPM(unique);
            if (rpmExisted) {
                domain.getTags().add("RPM");
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
