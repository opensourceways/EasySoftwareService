package com.easysoftware.application.domainpackage;

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
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.ranking.Ranker;
import com.easysoftware.redis.RedisGateway;
import com.easysoftware.redis.RedisUtil;
import jakarta.annotation.Resource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class DomainPackageServiceImpl implements DomainPackageService {

    /**
     * Logger for DomainPackageServiceImpl class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainPackageServiceImpl.class);

    /**
     * Resource injection for the ApplicationPackageService.
     */
    @Resource
    private ApplicationPackageService appPkgService;

    /**
     * Resource injection for the RPMPackageService.
     */
    @Resource
    private RPMPackageService rPMPkgService;

    /**
     * Resource injection for the EPKGPackageService.
     */
    @Resource
    private EPKGPackageService epkgPackageService;

    /**
     * Resource injection for the RPMPackageGateway.
     */
    @Resource
    private RPMPackageGateway rpmPackageGateway;

    /**
     * Resource injection for the EPKGPackageGateway.
     */
    @Resource
    private EPKGPackageGateway epkgPackageGateway;

    /**
     * Resource injection for the ApplicationPackageGateway.
     */
    @Resource
    private ApplicationPackageGateway applicationPackageGateway;

    /**
     * Resource injection for the RedisGateway.
     */
    @Resource
    private RedisGateway redisGateway;

    /**
     * Resource injection for the Ranker.
     */
    @Resource
    private Ranker ranker;

    /**
     * Timeout value for Redis global expiration.
     */
    @Value("${redis-global.expiration}")
    private int timeOut;

    /**
     * Search for domains based on the provided search condition.
     *
     * @param condition The DomainSearchCondition for searching.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchDomain(final DomainSearchCondition condition) {
        String name = condition.getName();
        String entity = condition.getEntity();

        // 搜索domain页面多个软件包
        if (StringUtils.isBlank(entity) && StringUtils.isNotBlank(name)) {
            return searchDomainPage(condition);
            // 搜索domain页面单个软件包
        } else if (StringUtils.isBlank(name) && StringUtils.isNotBlank(entity)) {
            return searchDomainEntity(condition);
        } else {
            throw new ParamErrorException("unspecified param: name, entity");
        }
    }

    /**
     * Search domain entity based on the provided search condition.
     *
     * @param condition The DomainSearchCondition for searching.
     * @return ResponseEntity<Object>.
     */
    private ResponseEntity<Object> searchDomainEntity(final DomainSearchCondition condition) {
        String entity = condition.getEntity();
        DomainPackageMenuVo domain = new DomainPackageMenuVo();
        domain.setName(entity);
        extendIds(domain);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", "-1"),
                Map.entry("list", domain));
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search for a domain page based on the provided search condition.
     *
     * @param condition The DomainSearchCondition for searching.
     * @return ResponseEntity<Object> containing the search results.
     */
    private ResponseEntity<Object> searchDomainPage(final DomainSearchCondition condition) {
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
            return searchAllEntity(condition);
        } else {
            throw new ParamErrorException("the value of parameter name: apppkg, rpmpkg, epkgpkg, all");
        }
    }

    /**
     * Search application package page based on the provided search condition.
     *
     * @param condition The DomainSearchCondition for searching.
     * @return A Map containing the search results.
     */
    private Map<String, Object> searchAppPkgPage(final DomainSearchCondition condition) {
        ApplicationPackageSearchCondition appCon = DomainPackageConverter.toApp(condition);
        return applicationPackageGateway.queryMenuByName(appCon);
    }

    /**
     * Search all entities based on the provided search condition.
     *
     * @param condition The DomainSearchCondition for searching.
     * @return ResponseEntity containing the search results.
     */
    private ResponseEntity<Object> searchAllEntity(final DomainSearchCondition condition) {
        // 根据请求参数生成唯一redis key
        String redisKeyStr = RedisUtil.objectToString(condition);
        String redisKeyFormat = "domainPage_%s";
        String redisKey = String.format(Locale.ROOT, redisKeyFormat, DigestUtils.sha256Hex(redisKeyStr));
        try {
            // 结果未过期，直接返回
            if (redisGateway.hasKey(redisKey)) {
                String resJson = redisGateway.get(redisKey);
                Object res = RedisUtil.convertToObject(resJson);
                return ResultUtil.success(HttpStatus.OK, res);
            }
        } catch (Exception e) {
            LOGGER.info(MessageCode.EC00015.getMsgEn());
        }

        ApplicationPackageSearchCondition appCon = DomainPackageConverter.toApp(condition);
        appCon.setPageSize(Integer.MAX_VALUE);
        List<ApplicationPackageMenuVo> appMenus = appPkgService.queryPkgMenuList(appCon);

        RPMPackageSearchCondition rpmCon = DomainPackageConverter.toRpm(condition);
        rpmCon.setPageSize(Integer.MAX_VALUE);
        rpmCon.setTimeOrder("");
        List<RPMPackageDomainVo> rpmMenus = rPMPkgService.queryPartAppPkgMenu(rpmCon);

        List<DomainPackageMenuVo> domainMenus = mergeMenuVOs(appMenus, rpmMenus);
        // 对 domainMenus 中的每个菜单调用 extendIds 方法。
        domainMenus.forEach(this::extendIds);
        Map<String, List<Object>> cateMap = getCategorys();
        // 对 domainMenus 中的每个菜单，将其添加到 cateMap 中对应类别的列表中。
        domainMenus.forEach(menu -> cateMap.get(menu.getCategory()).add(menu));
        List<Map<String, Object>> mapList = assembleMap(cateMap);
        List<Map<String, Object>> rankedMapList = ranker.rankingDomainPageByOperationConfig(mapList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", domainMenus.size()),
                Map.entry("list", rankedMapList));
        try {
            // 结果转json
            String resJson = RedisUtil.convertToJson(res);
            // 设置超时时间 配置默认超时时间
            redisGateway.setWithExpire(redisKey, resJson, timeOut, TimeUnit.HOURS);
        } catch (Exception e) {
            LOGGER.info(MessageCode.EC00015.getMsgEn());
        }
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Extend IDs for a DomainPackageMenuVo object.
     *
     * @param domain The DomainPackageMenuVo to extend IDs for.
     */
    private void extendIds(final DomainPackageMenuVo domain) {
        String name = domain.getName();
        Set<String> tags = domain.getTags();

        ApplicationPackageMenuVo app = applicationPackageGateway.selectOne(name);
        if (StringUtils.isNotBlank(app.getPkgId())) {
            tags.add("IMAGE");
            domain.getPkgIds().put("IMAGE", app.getPkgId());
        }

        RPMPackageMenuVo rpm = rpmPackageGateway.selectOne(name);
        if (StringUtils.isNotBlank(rpm.getPkgId())) {
            tags.add("RPM");
            domain.getPkgIds().put("RPM", rpm.getPkgId());
        }

        EPKGPackageMenuVo epkg = epkgPackageGateway.selectOne(name);
        if (StringUtils.isNotBlank(epkg.getPkgId())) {
            tags.add("EPKG");
            domain.getPkgIds().put("EPKG", epkg.getPkgId());
        }

    }

    /**
     * Merge menu VOs from ApplicationPackageMenuVo and RPMPackageDomainVo.
     *
     * @param appMenus List of ApplicationPackageMenuVo instances.
     * @param rpmMenus List of RPMPackageDomainVo instances.
     * @return List of merged DomainPackageMenuVo instances.
     */
    private List<DomainPackageMenuVo> mergeMenuVOs(final List<ApplicationPackageMenuVo> appMenus,
            final List<RPMPackageDomainVo> rpmMenus) {
        Map<String, DomainPackageMenuVo> domainMap = new HashMap<>();
        // 遍历 appMenus 列表
        appMenus.forEach(app -> {
            // 创建新的 DomainPackageMenuVo 对象，复制属性并设置标签和包 ID，然后将其放入 domainMap 中
            DomainPackageMenuVo domain = new DomainPackageMenuVo();
            BeanUtils.copyProperties(app, domain);
            domain.getTags().add("IMAGE");
            domain.getPkgIds().put("IMAGE", app.getPkgId());
            domainMap.put(app.getName(), domain);
        });

        // 遍历 rpmMenus 列表
        rpmMenus.forEach(rpm -> {
            String name = rpm.getName();
            // 如果 domainMap 包含名称，则将 "RPM" 添加到标签中并继续下一个循环
            if (domainMap.containsKey(name)) {
                domainMap.get(name).getTags().add("RPM");
                return;
            }
            // 创建新的 DomainPackageMenuVo 对象，复制属性并设置标签和包 ID，然后将其放入 domainMap 中
            DomainPackageMenuVo domain = new DomainPackageMenuVo();
            BeanUtils.copyProperties(rpm, domain);
            domain.getTags().add("RPM");
            domain.getPkgIds().put("RPM", rpm.getPkgId());
            domainMap.put(name, domain);
        });

        return new ArrayList<>(domainMap.values());
    }

    /**
     * Get categories with corresponding objects.
     *
     * @return A Map containing category names as keys and lists of objects as
     *         values.
     */
    private Map<String, List<Object>> getCategorys() {
        Map<String, List<Object>> map = new HashMap<>();
        // 使用流遍历所有的 AppCategoryEnum 值，将别名映射为新的 ArrayList 放入 map 中。
        Arrays.stream(AppCategoryEnum.values())
                .map(AppCategoryEnum::getAlias)
                .forEach(category -> map.put(category, new ArrayList<>()));
        return map;
    }

    /**
     * Assemble a list of maps from a map of objects.
     *
     * @param mapObj The map of objects to assemble.
     * @return A list of maps containing the assembled data.
     */
    private List<Map<String, Object>> assembleMap(final Map<String, List<Object>> mapObj) {
        List<Map<String, Object>> res = new ArrayList<>();
        mapObj.forEach((category, list) -> {
            // 为支持排序需求，修改为可变map
            Map<String, Object> map = new HashMap<>();
            map.put("name", category);
            map.put("children", list);
            res.add(map);
        });
        return res;
    }

    /**
     * Search for columns based on the provided condition.
     *
     * @param condition The DomainColumnCondition for searching.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchColumn(final DomainColumnCondition condition) {
        List<String> columns = QueryWrapperUtil.splitStr(condition.getColumn());
        if ("rpmpkg".equals(condition.getName())) {
            Map<String, List<String>> res = rpmPackageGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("epkgpkg".equals(condition.getName())) {
            Map<String, List<String>> res = epkgPackageGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else if ("apppkg".equals(condition.getName())) {
            Map<String, List<String>> res = applicationPackageGateway.queryColumn(columns);
            return ResultUtil.success(HttpStatus.OK, res);
        } else {
            throw new ParamErrorException("the value of parameter name: apppkg, rpmpkg, epkgpkg, all");
        }
    }

    /**
     * Query statistics.
     *
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> queryStat() {
        long rpmNum = rpmPackageGateway.queryTableLength();
        Long appNum = applicationPackageGateway.queryTableLength();
        long epkgNum = epkgPackageGateway.queryTableLength();

        Map<String, Long> res = Map.ofEntries(
                Map.entry("apppkg", appNum),
                Map.entry("total", Math.addExact(rpmNum, epkgNum)));
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search for domain details based on the provided search condition.
     *
     * @param condition The DomainDetailSearchCondition for searching.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchDomainDetail(final DomainDetailSearchCondition condition) {
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

    /**
     * Search for EPKG package detail based on the provided EPKG package ID.
     *
     * @param epkgPkgId The EPKG package ID to search for.
     * @return An EPKGPackageDetailVo object containing the details.
     */
    private EPKGPackageDetailVo searchEpkgDetail(final String epkgPkgId) {
        List<EPKGPackageDetailVo> epkgList = epkgPackageGateway.queryDetailByPkgId(epkgPkgId);
        if (epkgList.size() != 1) {
            throw new ParamErrorException(String.format(Locale.ROOT, MessageCode.EC00014.getMsgEn(), "epkgPkgId"));
        }
        return epkgList.get(0);
    }

    /**
     * Search for application package detail based on the provided application
     * package ID.
     *
     * @param appPkgId The application package ID to search for.
     * @return An ApplicationPackageDetailVo object containing the details.
     */
    private ApplicationPackageDetailVo searchAppDetail(final String appPkgId) {
        List<ApplicationPackageDetailVo> appList = applicationPackageGateway.queryDetailByPkgId(appPkgId);
        if (appList.size() != 1) {
            throw new ParamErrorException(String.format(Locale.ROOT, MessageCode.EC00014.getMsgEn(), "appPkgId"));
        }
        return appList.get(0);
    }

    /**
     * Search for RPM package detail based on the provided RPM package ID.
     *
     * @param rpmPkgId The RPM package ID to search for.
     * @return An RPMPackageDetailVo object containing the details.
     */
    private RPMPackageDetailVo searchRpmDetail(final String rpmPkgId) {
        List<RPMPackageDetailVo> rpmList = rpmPackageGateway.queryDetailByPkgId(rpmPkgId);
        if (rpmList.size() != 1) {
            throw new ParamErrorException(String.format(Locale.ROOT, MessageCode.EC00014.getMsgEn(), "rpmPkgId"));
        }
        return rpmList.get(0);
    }
}
