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
package com.easysoftware.infrastructure.oepkg.gatewalmpl.coverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.easysoftware.application.oepackage.vo.OEPackageDetailVo;
import com.easysoftware.application.oepackage.vo.OEPackageMenuVo;
import com.easysoftware.application.oepackage.vo.OepkgEulerVersionVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.infrastructure.oepkg.gatewalmpl.dataobject.OepkgDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;

public final class OEPackageConverter {
    /**
     * Logger instance for RPMPackageConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RPMPackageConverter.class);

    // Private constructor to prevent instantiation of the PackageConstant class
    private OEPackageConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("RPMPackageConverter class cannot be instantiated.");
    }

    /**
     * Converts a list of OEpkgDOs objects to a list of OEPackageDetailVo view
     * objects.
     *
     * @param OEpkgDOs The list of OepkgDO objects to convert.
     * @return A list of OEPackageDetailVo view objects.
     */
    public static List<OEPackageDetailVo> toDetail(final List<OepkgDO> OEpkgDOs) {
        List<OEPackageDetailVo> res = new ArrayList<>();
        for (OepkgDO rpm : OEpkgDOs) {
            OEPackageDetailVo detail = new OEPackageDetailVo();
            BeanUtils.copyProperties(rpm, detail);

            res.add(detail);
        }
        return res;
    }

    /**
     * Extracts a specific column from a list of OEpkgDOs objects and returns it
     * as a list of strings.
     *
     * @param OepkgDOs The list of OEPackageDO objects.
     * @param column   The name of the column to extract.
     * @return A list of strings representing the extracted column values.
     */
    public static List<String> toColumn(final List<OepkgDO> OepkgDOs, final String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = OepkgDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (OepkgDO oekgDO : OepkgDOs) {
                if (oekgDO == null) {
                    continue;
                }
                Object obj = field.get(oekgDO);

                if (obj == null) {
                    LOGGER.warn("Field value is null for pkg: {}", oekgDO);
                    continue;
                }

                if (!(obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(oekgDO);
                res.add(value);
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e.getMessage());
        }
        return res;
    }

    /**
     * Converts a list of OepkgDO objects to a list of OEPackageMenuVo view
     * objects.
     *
     * @param oekgDOs The list of OepkgDO objects to convert.
     * @return A list of OEPackageMenuVo view objects.
     */
    public static List<OEPackageMenuVo> toMenu(final List<OepkgDO> oekgDOs) {
        List<OEPackageMenuVo> res = new ArrayList<>();
        for (OepkgDO oepkgDO : oekgDOs) {
            OEPackageMenuVo menu = new OEPackageMenuVo();
            BeanUtils.copyProperties(oepkgDO, menu);
            res.add(menu);
        }
        return res;
    }

    /**
     * Converts a list of RPMPackageDO objects to a list of OepkgEulerVersionVo
     * view
     * objects.
     *
     * @param rpmPkgDOs The list of OepkgDO objects to convert.
     * @return A list of OepkgEulerVersionVo view objects.
     */
    public static List<OepkgEulerVersionVo> toVersion(final List<OepkgDO> rpmPkgDOs) {
        List<OepkgEulerVersionVo> res = new ArrayList<>();
        for (OepkgDO rpm : rpmPkgDOs) {
            OepkgEulerVersionVo version = new OepkgEulerVersionVo();
            version.setOs(rpm.getOs());
            version.setArch(rpm.getArch());
            version.setPkgId(rpm.getPkgId());
            res.add(version);
        }
        return SortUtil.sortEulerVer(res);
    }
}
