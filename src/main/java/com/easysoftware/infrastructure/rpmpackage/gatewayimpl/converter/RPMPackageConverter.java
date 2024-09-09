/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter;

import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageEulerVersionVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageNewestVersionVo;
import com.easysoftware.application.rpmpackage.vo.PackgeVersionVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.infrastructure.oepkg.gatewalmpl.dataobject.OepkgDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                if (obj == null) {
                    LOGGER.warn("Field value is null for pkg: {}", rPMPkgDO);
                    continue;
                }

                if (!(obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(rPMPkgDO);
                res.add(value);
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e.getMessage());
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
        return SortUtil.sortEulerVer(res);
    }


    /**
     * Converts a list of RPMPackageDO objects to a list of
     * RPMPackageNewestVersionVo
     * view
     * objects.
     *
     * @param rpmPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackageNewestVersionVo view objects.
     */
    public static List<RPMPackageNewestVersionVo> toRPMVersion(final List<RPMPackageDO> rpmPkgDOs) {
        if (rpmPkgDOs == null || rpmPkgDOs.isEmpty()) {
            return Collections.emptyList();
        }

        List<RPMPackageNewestVersionVo> res = new ArrayList<>();
        String newestVersion = "";
        String os = "";

        if (rpmPkgDOs.size() > 0) {
            newestVersion = rpmPkgDOs.get(0).getVersion();
        }

        for (RPMPackageDO rpm : rpmPkgDOs) {
            if (newestVersion.compareTo(rpm.getVersion()) <= 0) {
                newestVersion = rpm.getVersion();
                os = rpm.getOs();
            }
        }

        RPMPackageNewestVersionVo version = new RPMPackageNewestVersionVo();
        version.setNewestVersion(newestVersion);
        version.setOs(os);
        res.add(version);
        return res;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of
     * RPMPackgeVersionVo
     * view
     * objects.
     *
     * @param rpmPkgDOs The list of RPMPackageDO objects to convert.
     * @return A list of RPMPackageNewestVersionVo view objects.
     */
    public static Map<String, List<PackgeVersionVo>> toRPMVersions(final Map<String, List<RPMPackageDO>> rpmPkgDOs) {
        if (rpmPkgDOs == null || rpmPkgDOs.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<PackgeVersionVo>> res = new HashMap<>();

        for (Map.Entry<String, List<RPMPackageDO>> entry : rpmPkgDOs.entrySet()) {
            List<RPMPackageDO> packages = entry.getValue();
            for (RPMPackageDO pkg: packages) {
                PackgeVersionVo vo = new PackgeVersionVo();
                vo.setName(pkg.getName());
                vo.setVersion(pkg.getVersion());
                vo.setOs(pkg.getOs());
                res.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(vo);
            }
        }
        return res;
    }

    /**
     * Converts a list of OepkgDO objects to a list of
     * RPMPackageNewestVersionVo
     * view
     * objects.
     *
     * @param oList The list of OepkgDO objects to convert.
     * @return A list of RPMPackageNewestVersionVo view objects.
     */
    public static List<RPMPackageNewestVersionVo> toRPMVersionFromOepkg(final List<OepkgDO> oList) {
        if (oList == null || oList.isEmpty()) {
            return Collections.emptyList();
        }

        List<RPMPackageDO> rList = new ArrayList<>();
        for (OepkgDO o : oList) {
            RPMPackageDO r = new RPMPackageDO();
            BeanUtils.copyProperties(o, r);
            rList.add(r);
        }

        return toRPMVersion(rList);
    }
}
