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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;
import com.easysoftware.application.applicationpackage.ApplicationPackageService;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.domainpackage.converter.DomainPackageConverter;
import com.easysoftware.application.domainpackage.dto.DomainColumnCondition;
import com.easysoftware.application.domainpackage.dto.DomainDetailSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;
import com.easysoftware.application.domainpackage.vo.DomainPackageMenuVo;
import com.easysoftware.application.epkgpackage.EPKGPackageService;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.application.rpmpackage.RPMPackageService;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConverter;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.redis.RedisService;
import com.easysoftware.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Autowired  
    private RedisService redisService;  

    @Value("${redis-global.expiration}") 
    int timeOut;

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
            Map<String, Object> res = searchAppPkgPage(condition);
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

    private Map<String, Object> searchAppPkgPage(DomainSearchCondition condition) {
        ApplicationPackageSearchCondition appCon = DomainPackageConverter.toApp(condition);
        Map<String, Object> map = applicationPackageGateway.queryMenuByName(appCon);
        List<ApplicationPackageMenuVo> appList = (List<ApplicationPackageMenuVo>) map.get("list");

        Map<String, List<Object>> cateMap = getCategorys();
        for (ApplicationPackageMenuVo app : appList) {
            String cate = StringUtils.trimToEmpty(app.getCategory());
            cateMap.get(cate).add(app);
        }
        List<Map<String, Object>> mapList = assembleMap(cateMap);

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", map.get("total")),
            Map.entry("list", mapList)
        );
        return res;
    }

    private ResponseEntity<Object> searchAllEntity(DomainSearchCondition condition) {
        // 根据请求参数生成唯一redis key
        String redisKeyStr = RedisUtil.objectToString(condition);
        String redisKey = RedisUtil.getSHA256(redisKeyStr);
        
        // 结果未过期，直接返回
        if(redisService.hasKey(redisKey) == true){
            String resJson = redisService.get(redisKey);
            Object res = RedisUtil.convertToObject(resJson);
            return ResultUtil.success(HttpStatus.OK, res);
        }

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

        Map<String, List<Object>> cateMap = getCategorys();
        for (DomainPackageMenuVo menu : domainMenus) {
            String cate = menu.getCategory();
            cateMap.get(cate).add(menu);
        }
        List<Map<String, Object>> mapList = assembleMap(cateMap);

        Map res = Map.ofEntries(
            Map.entry("total", domainMenus.size()),
            Map.entry("list", mapList)
        );
        
        // 结果转json
        String resJson = RedisUtil.convertToJson(res);
       // 设置超时时间 
        redisService.setWithExpire(redisKey, resJson, timeOut, TimeUnit.HOURS);
       
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

    private Map<String, List<Object>> getCategorys() {
        Map<String, List<Object>> map = new HashMap<>();
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            String category = categoryEnum.getAlias();
            map.put(category, new ArrayList<>());
        }
        return map;
    }

    private List<Map<String, Object>> assembleMap(Map<String, List<Object>> mapObj) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (Map.Entry<String, List<Object>> entry : mapObj.entrySet()) {
            String category = entry.getKey();
            List<Object> list = entry.getValue();
            res.add(Map.ofEntries(
                Map.entry("name", category),
                Map.entry("children", list)
            ));
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

    @Override
    public ResponseEntity<Object> searchDomainDetail(DomainDetailSearchCondition condition) {
        Map<String, Object> res = new HashMap<>();
        Set<String> tags = new HashSet<>();

        String appPkgId = condition.getAppPkgId();
        if (StringUtils.isNotBlank(appPkgId)) {
            ApplicationPackageDetailVo app = searchAppDetail(appPkgId);
            res.put("IMAGE", app);
            tags.add("IMAGE");
        }

        String rpmPkgId = condition.getRpmPkgId();
        if (StringUtils.isNotBlank(rpmPkgId)) {
            RPMPackageDetailVo rpm = searchRpmDetail(rpmPkgId);
            res.put("RPM", rpm);
            tags.add("RPM");
        }

        String epkgPkgId = condition.getEpkgPkgId();
        if (StringUtils.isNotBlank(epkgPkgId)) {
            EPKGPackageDetailVo epkg = searchEpkgDetail(epkgPkgId);
            res.put("EPKG", epkg);
            tags.add("EPKG");
        }

        res.put("tags", tags);
        return ResultUtil.success(HttpStatus.OK, res);
        
    }

    private EPKGPackageDetailVo searchEpkgDetail(String epkgPkgId) {
        List<EPKGPackageDetailVo> epkgList = epkgPackageGateway.queryDetailByPkgId(epkgPkgId);
        if (epkgList.size() != 1) {
            throw new ParamErrorException(String.format(MessageCode.EC00014.getMsgEn(), "epkgPkgId"));
        }
        return epkgList.get(0);
    }

    private ApplicationPackageDetailVo searchAppDetail(String appPkgId) {
        List<ApplicationPackageDetailVo> appList = applicationPackageGateway.queryDetailByPkgId(appPkgId);
        if (appList.size() != 1) {
            throw new ParamErrorException(String.format(MessageCode.EC00014.getMsgEn(), "appPkgId"));
        }
        return appList.get(0);
    }

    private RPMPackageDetailVo searchRpmDetail(String rpmPkgId) {
        List<RPMPackageDetailVo> rpmList = rpmPackageGateway.queryDetailByPkgId(rpmPkgId);
        if (rpmList.size() != 1) {
            throw new ParamErrorException(String.format(MessageCode.EC00014.getMsgEn(), "rpmPkgId"));
        }
        return rpmList.get(0);
    }
}
