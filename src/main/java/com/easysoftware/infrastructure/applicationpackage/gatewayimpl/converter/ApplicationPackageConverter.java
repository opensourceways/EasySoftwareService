package com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter;

import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageEulerArchsVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageEulerVersionVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageTagsVo;
import com.easysoftware.application.domainpackage.vo.DomainPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ApplicationPackageConverter {

    // Private constructor to prevent instantiation of the utility class
    private ApplicationPackageConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate ApplicationPackageConverter class");
    }

    /**
     * Logger instance for ApplicationPackageConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationPackageConverter.class);

    /**
     * Convert an ApplicationPackageDO object to an ApplicationPackage entity.
     *
     * @param appPkgDO The ApplicationPackageDO object to convert
     * @return An ApplicationPackage entity
     */
    public static ApplicationPackage toEntity(final ApplicationPackageDO appPkgDO) {
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(appPkgDO, appPkg);
        return appPkg;
    }

    /**
     * Converts a list of ApplicationPackageDO objects to a list of
     * ApplicationPackageEulerVersionVo
     * view
     * objects.
     *
     * @param appkgDOs The list of ApplicationPackageDO objects to convert.
     * @return A list of ApplicationPackageEulerVersionVo view objects.
     */
    public static List<ApplicationPackageEulerVersionVo> toVersion(final List<ApplicationPackageDO> appkgDOs) {
        List<ApplicationPackageEulerVersionVo> res = new ArrayList<>();
        for (ApplicationPackageDO appkg : appkgDOs) {
            ApplicationPackageEulerVersionVo version = new ApplicationPackageEulerVersionVo();
            version.setOs(appkg.getOs());
            version.setArch(appkg.getArch());
            version.setPkgId(appkg.getPkgId());
            res.add(version);
        }
        return res;
    }

    /**
     * Converts a list of ApplicationPackageDO objects to a list of
     * ApplicationPackageEulerArchsVo
     * view
     * objects.
     *
     * @param appkgDOs The list of ApplicationPackageDO objects to convert.
     * @return A list of ApplicationPackageEulerArchsVo view objects.
     */
    public static List<ApplicationPackageEulerArchsVo> toArchs(final List<ApplicationPackageDO> appkgDOs) {
        List<ApplicationPackageEulerArchsVo> res = new ArrayList<>();
        for (ApplicationPackageDO appkg : appkgDOs) {
            ApplicationPackageEulerArchsVo archs = new ApplicationPackageEulerArchsVo();
            archs.setArch(appkg.getArch());
            res.add(archs);
        }
        return res;
    }

    /**
     * Convert a list of ApplicationPackageDO objects to a list of
     * ApplicationPackageDetailVo objects.
     *
     * @param appPkgDOs The list of ApplicationPackageDO objects to convert
     * @return A list of ApplicationPackageDetailVo objects
     */
    public static List<ApplicationPackageDetailVo> toDetail(final List<ApplicationPackageDO> appPkgDOs) {
        List<ApplicationPackageDetailVo> res = new ArrayList<>();
        for (ApplicationPackageDO app : appPkgDOs) {
            res.add(toDetail(app));
        }
        return res;
    }

    /**
     * Convert an ApplicationPackageDO object to an ApplicationPackageDetailVo
     * object.
     *
     * @param app The ApplicationPackageDO object to convert
     * @return An ApplicationPackageDetailVo object
     */
    public static ApplicationPackageDetailVo toDetail(final ApplicationPackageDO app) {
        ApplicationPackageDetailVo detail = new ApplicationPackageDetailVo();
        BeanUtils.copyProperties(app, detail);
        return detail;
    }

    /**
     * Aggregate a list of ApplicationPackageDO objects by tags
     * and return the result as a list of ApplicationPackageTagsVo objects.
     *
     * @param appPkgDOs The list of ApplicationPackageDO objects to aggregate
     * @return A list of ApplicationPackageTagsVo objects containing the aggregated
     *         data
     */
    public static List<ApplicationPackageTagsVo> aggregateByTags(final List<ApplicationPackageDO> appPkgDOs) {

        List<ApplicationPackageTagsVo> appTags = new ArrayList<>();

        for (ApplicationPackageDO app : appPkgDOs) {
            ApplicationPackageTagsVo tag = new ApplicationPackageTagsVo();
            tag.setAppVer(app.getAppVer());
            tag.setArch(app.getArch());
            tag.setDockerStr("docker pull openeuler/".concat(app.getName()).concat(":").concat(app.getAppVer()));
            appTags.add(tag);
        }

        Map<String, ApplicationPackageTagsVo> agrMap = new HashMap<>();
        for (ApplicationPackageTagsVo tag : appTags) {
            if (agrMap.containsKey(tag.getAppVer())) {
                // 该tag下已经存在，开始合并
                ApplicationPackageTagsVo mergeTag = agrMap.get(tag.getAppVer());
                String mergedArch = mergeTag.getArch().concat(",").concat(tag.getArch());
                mergeTag.setArch(mergedArch);
            } else {
                agrMap.put(tag.getAppVer(), tag);
            }
        }

        List<ApplicationPackageTagsVo> resTags = new ArrayList<>();

        for (Map.Entry<String, ApplicationPackageTagsVo> entry : agrMap.entrySet()) {

            ApplicationPackageTagsVo agrTag = entry.getValue();
            resTags.add(agrTag);
        }

        return resTags;
    }

    /**
     * Convert a list of ApplicationPackageDO objects to a list of
     * ApplicationPackageMenuVo objects.
     *
     * @param appPkgDOs The list of ApplicationPackageDO objects to convert
     * @return A list of ApplicationPackageMenuVo objects
     */
    public static List<ApplicationPackageMenuVo> toMenu(final List<ApplicationPackageDO> appPkgDOs) {
        List<ApplicationPackageMenuVo> res = new ArrayList<>();
        for (ApplicationPackageDO app : appPkgDOs) {
            ApplicationPackageMenuVo menu = new ApplicationPackageMenuVo();
            BeanUtils.copyProperties(app, menu);

            res.add(menu);
        }
        return res;
    }

    /**
     * Convert an ApplicationPackageDO object to an ApplicationPackageMenuVo object.
     *
     * @param appPkgDO The ApplicationPackageDO object to convert
     * @return An ApplicationPackageMenuVo object
     */
    public static ApplicationPackageMenuVo toMenu(final ApplicationPackageDO appPkgDO) {
        ApplicationPackageMenuVo menu = new ApplicationPackageMenuVo();
        BeanUtils.copyProperties(appPkgDO, menu);
        return menu;
    }

    /**
     * Convert a list of ApplicationPackageDO objects to a list of
     * ApplicationPackage entities.
     *
     * @param appDOs The list of ApplicationPackageDO objects to convert
     * @return A list of ApplicationPackage entities
     */
    public static List<ApplicationPackage> toEntity(final List<ApplicationPackageDO> appDOs) {
        List<ApplicationPackage> res = new ArrayList<>();
        for (ApplicationPackageDO appDO : appDOs) {
            ApplicationPackage app = toEntity(appDO);
            res.add(app);
        }
        return res;
    }

    /**
     * Convert an ApplicationPackage entity to an ApplicationPackageDO data object.
     *
     * @param appPkg The ApplicationPackage entity to convert
     * @return An ApplicationPackageDO data object
     */
    public static ApplicationPackageDO toDataObject(final ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = new ApplicationPackageDO();
        BeanUtils.copyProperties(appPkg, appPkgDO);
        return appPkgDO;
    }

    /**
     * Convert an ApplicationPackage entity to an ApplicationPackageDO data object
     * specifically for creation.
     *
     * @param appPkg The ApplicationPackage entity to convert
     * @return An ApplicationPackageDO data object tailored for creation
     */
    public static ApplicationPackageDO toDataObjectForCreate(final ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = toDataObject(appPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        appPkgDO.setCreateAt(currentTime);
        appPkgDO.setUpdateAt(currentTime);
        appPkgDO.setId(id);

        return appPkgDO;
    }

    /**
     * Convert an ApplicationPackage entity to an ApplicationPackageDO data object
     * specifically for update.
     *
     * @param appPkg The ApplicationPackage entity to convert
     * @return An ApplicationPackageDO data object tailored for update
     */
    public static ApplicationPackageDO toDataObjectForUpdate(final ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = toDataObject(appPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        appPkgDO.setUpdateAt(currentTime);

        return appPkgDO;
    }

    /**
     * Convert a list of ApplicationPackageMenuVo objects to a list of
     * DomainPackageMenuVo objects.
     *
     * @param appList The list of ApplicationPackageMenuVo objects to convert
     * @return A list of DomainPackageMenuVo objects
     */
    public static List<DomainPackageMenuVo> toDomainPackageMenuVo(final List<ApplicationPackageMenuVo> appList) {
        List<DomainPackageMenuVo> menuList = new ArrayList<>();
        for (ApplicationPackageMenuVo app : appList) {
            DomainPackageMenuVo menu = toDomainPackageMenuVo(app);
            menuList.add(menu);
        }
        return menuList;
    }

    /**
     * Convert an ApplicationPackageMenuVo object to a DomainPackageMenuVo object.
     *
     * @param app The ApplicationPackageMenuVo object to convert
     * @return A DomainPackageMenuVo object
     */
    public static DomainPackageMenuVo toDomainPackageMenuVo(final ApplicationPackageMenuVo app) {
        DomainPackageMenuVo menu = new DomainPackageMenuVo();
        BeanUtils.copyProperties(app, menu);
        menu.getPkgIds().put("IMAGE", app.getPkgId());
        menu.getTags().add("IMAGE");
        return menu;
    }

    /**
     * Extract a specific column from a list of ApplicationPackageDO objects
     * and return the results as a list of strings.
     *
     * @param appDOs The list of ApplicationPackageDO objects
     * @param column The column to extract
     * @return A list of strings representing the values in the specified column
     */
    public static List<String> toColumn(final List<ApplicationPackageDO> appDOs, final String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = ApplicationPackageDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (ApplicationPackageDO appDo : appDOs) {
                if (appDo == null) {
                    continue;
                }
                Object obj = field.get(appDo);
                if (!(obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(appDo);
                if (StringUtils.isNotBlank(value)) {
                    res.add(value);
                }

            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e);
        }
        return res;
    }
}
