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
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.ResultUtil;
import jakarta.annotation.Resource;

@Service
public class DomainPackageServiceImpl implements DomainPackageService {
    @Resource
    ApplicationPackageService appPkgService;

    @Resource
    RPMPackageService rPMPkgService;

    @Override
    public ResponseEntity<Object> searchDomain(DomainSearchCondition condition) {
        if ("apppkg".equals(condition.getName())) {
            ApplicationPackageSearchCondition appCon = new ApplicationPackageSearchCondition();
            BeanUtils.copyProperties(condition, appCon);
            List<ApplicationPackageMenuVo> appMenuList = appPkgService.queryAllAppPkgMenu(appCon);
            List<DomainPackageMenuVo> domainMenuList = new ArrayList<>();
            
            for (ApplicationPackageMenuVo appMenu : appMenuList) {
                DomainPackageMenuVo domainMenu = new DomainPackageMenuVo();
                BeanUtils.copyProperties(appMenu, domainMenu);
                domainMenu.setCategory(appMenu.getAppCategory());
                domainMenu.setTags(new ArrayList<String>(Arrays.asList(appMenu.getType())));
                domainMenuList.add(domainMenu);
            }

            List<Map<String, Object>> res = groupByCategory(domainMenuList);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("rpmpkg".equals(condition.getName())) {
            RPMPackageSearchCondition rPMCon = new RPMPackageSearchCondition();
            BeanUtils.copyProperties(condition, rPMCon);
            List<RPMPackageMenuVo> appMenuList = rPMPkgService.queryAllRPMPkgMenu(rPMCon);
            return ResultUtil.success(HttpStatus.OK, appMenuList);
        }
        return null;
    }

    private List<Map<String, Object>> groupByCategory(List<DomainPackageMenuVo> menuList) {
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
}
