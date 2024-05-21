package com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter;

import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageEulerArchsVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageEulerVersionVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class EPKGPackageConverter {

    // Private constructor to prevent instantiation of the utility class
    private EPKGPackageConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate EPKGPackageConverter class");
    }

    /**
     * Logger instance for EPKGPackageConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EPKGPackageConverter.class);

    /**
     * Convert a list of EPKGPackageDO objects to a list of EPKGPackageMenuVo
     * objects.
     *
     * @param rPMPkgDOs The list of EPKGPackageDO objects to convert
     * @return A list of EPKGPackageMenuVo objects
     */
    public static List<EPKGPackageMenuVo> toMenu(final List<EPKGPackageDO> rPMPkgDOs) {
        List<EPKGPackageMenuVo> res = new ArrayList<>();
        for (EPKGPackageDO rpm : rPMPkgDOs) {
            EPKGPackageMenuVo menu = toMenu(rpm);
            res.add(menu);
        }
        return res;
    }

    /**
     * Convert an EPKGPackageDO object to an EPKGPackageMenuVo object.
     *
     * @param epkg The EPKGPackageDO object to convert
     * @return An EPKGPackageMenuVo object
     */
    public static EPKGPackageMenuVo toMenu(final EPKGPackageDO epkg) {
        EPKGPackageMenuVo menu = new EPKGPackageMenuVo();
        BeanUtils.copyProperties(epkg, menu);
        return menu;
    }

    /**
     * Converts a list of EPKGPackageDO objects to a list of
     * EPKGPackageEulerVersionVo
     * view
     * objects.
     *
     * @param rpmPkgDOs The list of EPKGPackageDO objects to convert.
     * @return A list of EPKGPackageEulerVersionVo view objects.
     */
    public static List<EPKGPackageEulerVersionVo> toVersion(final List<EPKGPackageDO> epkgDOs) {
        List<EPKGPackageEulerVersionVo> res = new ArrayList<>();
        for (EPKGPackageDO epkg : epkgDOs) {
            EPKGPackageEulerVersionVo version = new EPKGPackageEulerVersionVo();
            version.setOs(epkg.getOs());
            res.add(version);
        }
        return res;
    }

    /**
     * Converts a list of EPKGPackageDO objects to a list of EPKGPackageEulerArchsVo
     * view
     * objects.
     *
     * @param rpmPkgDOs The list of EPKGPackageDO objects to convert.
     * @return A list of EPKGPackageEulerArchsVo view objects.
     */
    public static List<EPKGPackageEulerArchsVo> toArchs(final List<EPKGPackageDO> epkgDOs) {
        List<EPKGPackageEulerArchsVo> res = new ArrayList<>();
        for (EPKGPackageDO epkg : epkgDOs) {
            EPKGPackageEulerArchsVo archs = new EPKGPackageEulerArchsVo();
            archs.setArch(epkg.getArch());
            res.add(archs);
        }
        return res;
    }

    /**
     * Extract a specific column values from a list of EPKGPackageDO objects.
     *
     * @param epkgDOs The list of EPKGPackageDO objects
     * @param column  The column to extract values from
     * @return A list of values for the specified column
     */
    public static List<String> toColumn(final List<EPKGPackageDO> epkgDOs, final String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = EPKGPackageDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (EPKGPackageDO epkgDO : epkgDOs) {
                if (epkgDO == null) {
                    continue;
                }
                Object obj = field.get(epkgDO);
                if (!(obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(epkgDO);
                res.add(value);
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e);
        }
        return res;
    }

    /**
     * Convert a list of EPKGPackageDO objects to a list of EPKGPackageDetailVo
     * objects.
     *
     * @param rPMPkgDOs The list of EPKGPackageDO objects to convert
     * @return A list of EPKGPackageDetailVo objects
     */
    public static List<EPKGPackageDetailVo> toDetail(final List<EPKGPackageDO> rPMPkgDOs) {
        List<EPKGPackageDetailVo> res = new ArrayList<>();
        for (EPKGPackageDO rpm : rPMPkgDOs) {
            EPKGPackageDetailVo detail = new EPKGPackageDetailVo();
            BeanUtils.copyProperties(rpm, detail);
            res.add(detail);
        }
        return res;
    }

    /**
     * Convert an EPKGPackage entity to an EPKGPackageDO data object.
     *
     * @param epkg The EPKGPackage entity to convert
     * @return An EPKGPackageDO data object
     */
    public static EPKGPackageDO toDataObject(final EPKGPackage epkg) {
        EPKGPackageDO epkgPackageDO = new EPKGPackageDO();
        BeanUtils.copyProperties(epkg, epkgPackageDO);
        return epkgPackageDO;
    }

    /**
     * Convert an EPKGPackage entity to an EPKGPackageDO data object for creation.
     *
     * @param epkg The EPKGPackage entity to convert
     * @return An EPKGPackageDO data object for creation
     */
    public static EPKGPackageDO toDataObjectForCreate(final EPKGPackage epkg) {
        EPKGPackageDO epkgPackageDO = toDataObject(epkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        epkgPackageDO.setCreateAt(currentTime);
        epkgPackageDO.setUpdateAt(currentTime);
        epkgPackageDO.setId(id);

        return epkgPackageDO;
    }

    /**
     * Convert an EPKGPackage entity to an EPKGPackageDO data object for update.
     *
     * @param rPMPkg The EPKGPackage entity to convert
     * @return An EPKGPackageDO data object for update
     */
    public static EPKGPackageDO toDataObjectForUpdate(final EPKGPackage rPMPkg) {
        EPKGPackageDO rPMPkgDO = toDataObject(rPMPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        rPMPkgDO.setUpdateAt(currentTime);

        return rPMPkgDO;
    }
}
