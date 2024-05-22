package com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter;

import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageEulerArchsVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageEulerVersionVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class RPMPackageConverter {

    // Private constructor to prevent instantiation of the PackageConstant class
    private RPMPackageConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("RPMPackageConverter class cannot be instantiated.");
    }

    /**
     * Logger instance for RPMPackageConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RPMPackageConverter.class);

    /**
     * Converts an RPMPackageDO object to an RPMPackage entity.
     *
     * @param rPMPkgDO The RPMPackageDO object to convert.
     * @return The converted RPMPackage entity.
     */
    public static RPMPackage toEntity(final RPMPackageDO rPMPkgDO) {
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(rPMPkgDO, rPMPkg);
        return rPMPkg;
    }

    /**
     * Extracts a specific column from a list of RPMPackageDO objects and returns it
     * as a list of strings.
     *
     * @param rPMPkgDOs The list of RPMPackageDO objects.
     * @param column    The name of the column to extract.
     * @return A list of strings representing the extracted column values.
     */
    public static List<String> toColumn(final List<RPMPackageDO> rPMPkgDOs, final String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = RPMPackageDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (RPMPackageDO rPMPkgDO : rPMPkgDOs) {
                if (rPMPkgDO == null) {
                    continue;
                }
                Object obj = field.get(rPMPkgDO);
                if (!(obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(rPMPkgDO);
                res.add(value);
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e);
        }
        return res;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of RPMPackage entities.
     *
     * @param rPMPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackage entities.
     */
    public static List<RPMPackage> toEntity(final List<RPMPackageDO> rPMPkgDOs) {
        List<RPMPackage> res = new ArrayList<>();
        for (RPMPackageDO rPMPkgDO : rPMPkgDOs) {
            RPMPackage rPMPkg = toEntity(rPMPkgDO);
            res.add(rPMPkg);
        }
        return res;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of RPMPackageDetailVo view
     * objects.
     *
     * @param rPMPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackageDetailVo view objects.
     */
    public static List<RPMPackageDetailVo> toDetail(final List<RPMPackageDO> rPMPkgDOs) {
        List<RPMPackageDetailVo> res = new ArrayList<>();
        for (RPMPackageDO rpm : rPMPkgDOs) {
            RPMPackageDetailVo detail = new RPMPackageDetailVo();
            BeanUtils.copyProperties(rpm, detail);

            res.add(detail);
        }
        return res;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of RPMPackageMenuVo view
     * objects.
     *
     * @param rPMPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackageMenuVo view objects.
     */
    public static List<RPMPackageMenuVo> toMenu(final List<RPMPackageDO> rPMPkgDOs) {
        List<RPMPackageMenuVo> res = new ArrayList<>();
        for (RPMPackageDO rpm : rPMPkgDOs) {
            RPMPackageMenuVo menu = toMenu(rpm);
            res.add(menu);
        }

        return res;
    }

    /**
     * Converts an RPMPackageDO object to an RPMPackageMenuVo view object.
     *
     * @param rpm The RPMPackageDO object to convert.
     * @return The converted RPMPackageMenuVo view object.
     */
    public static RPMPackageMenuVo toMenu(final RPMPackageDO rpm) {
        RPMPackageMenuVo menu = new RPMPackageMenuVo();
        BeanUtils.copyProperties(rpm, menu);
        return menu;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of RPMPackageDomainVo view
     * objects.
     *
     * @param rpmPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackageDomainVo view objects.
     */
    public static List<RPMPackageDomainVo> toDomain(final List<RPMPackageDO> rpmPkgDOs) {
        List<RPMPackageDomainVo> res = new ArrayList<>();
        for (RPMPackageDO rpm : rpmPkgDOs) {
            RPMPackageDomainVo domain = new RPMPackageDomainVo();
            BeanUtils.copyProperties(rpm, domain);
            domain.setTags(List.of("RPM"));
            domain.setCategory(rpm.getCategory());

            res.add(domain);
        }
        return res;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of RPMPackageEulerVersionVo
     * view
     * objects.
     *
     * @param rpmPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackageEulerVersionVo view objects.
     */
    public static List<RPMPackageEulerVersionVo> toVersion(final List<RPMPackageDO> rpmPkgDOs) {
        List<RPMPackageEulerVersionVo> res = new ArrayList<>();
        for (RPMPackageDO rpm : rpmPkgDOs) {
            RPMPackageEulerVersionVo version = new RPMPackageEulerVersionVo();
            version.setOs(rpm.getOs());
            version.setArch(rpm.getArch());
            version.setPkgId(rpm.getPkgId());
            res.add(version);
        }
        return res;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of RPMPackageEulerArchsVo
     * view
     * objects.
     *
     * @param rpmPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackageEulerArchsVo view objects.
     */
    public static List<RPMPackageEulerArchsVo> toArchs(final List<RPMPackageDO> rpmPkgDOs) {
        List<RPMPackageEulerArchsVo> res = new ArrayList<>();
        for (RPMPackageDO rpm : rpmPkgDOs) {
            RPMPackageEulerArchsVo archs = new RPMPackageEulerArchsVo();
            archs.setArch(rpm.getArch());
            res.add(archs);
        }
        return res;
    }

    /**
     * Converts an RPMPackage entity to an RPMPackageDO data object.
     *
     * @param rPMPkg The RPMPackage entity to convert.
     * @return The converted RPMPackageDO data object.
     */
    public static RPMPackageDO toDataObject(final RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = new RPMPackageDO();
        BeanUtils.copyProperties(rPMPkg, rPMPkgDO);
        return rPMPkgDO;
    }

    /**
     * Converts an RPMPackage entity to an RPMPackageDO data object for creation.
     *
     * @param rPMPkg The RPMPackage entity to convert.
     * @return The converted RPMPackageDO data object for creation.
     */
    public static RPMPackageDO toDataObjectForCreate(final RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = toDataObject(rPMPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        rPMPkgDO.setCreateAt(currentTime);
        rPMPkgDO.setUpdateAt(currentTime);
        rPMPkgDO.setId(id);

        return rPMPkgDO;
    }

    /**
     * Converts an RPMPackage entity to an RPMPackageDO data object for update.
     *
     * @param rPMPkg The RPMPackage entity to convert.
     * @return The converted RPMPackageDO data object for update.
     */
    public static RPMPackageDO toDataObjectForUpdate(final RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = toDataObject(rPMPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        rPMPkgDO.setUpdateAt(currentTime);
        return rPMPkgDO;
    }
}
