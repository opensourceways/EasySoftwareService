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

package com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.fieldpkg.dto.FieldPkgSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.application.filedapplication.vo.OsArchNumVO;
import com.easysoftware.application.oepackage.dto.OEPackageSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject.FieldApplicationDO;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class FieldApplicationConverter {

    // Private constructor to prevent instantiation of the utility class
    private FieldApplicationConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate FieldApplicationConverter class");
    }

    /**
     * Logger instance for FieldApplicationConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldApplicationConverter.class);

    /**
     * Converts a list of FieldApplicationDO objects to a list of FieldApplicationVo
     * view objects.
     *
     * @param doList The list of FieldApplicationDO objects to convert.
     * @return A list of FieldApplicationVo view objects.
     */
    public static List<FiledApplicationVo> toVo(final List<FieldApplicationDO> doList) {
        List<FiledApplicationVo> res = new ArrayList<>();
        for (FieldApplicationDO op : doList) {
            FiledApplicationVo vo = toVo(op);
            res.add(vo);
        }
        return res;
    }

    /**
     * Converts a FieldApplicationDO object to a FieldApplicationVo view object.
     *
     * @param opDo The FieldApplicationDO object to convert.
     * @return The converted FieldApplicationVo view object.
     */
    public static FiledApplicationVo toVo(final FieldApplicationDO opDo) {
        FiledApplicationVo opVo = new FiledApplicationVo();
        BeanUtils.copyProperties(opDo, opVo);
        List<String> tags = ObjectMapperUtil.toObjectList(String.class, opDo.getTags());
        Map<String, Object> pkgIds = ObjectMapperUtil.toMap(opDo.getPkgIds());
        Map<String, Object> maintainers = ObjectMapperUtil.toMap(opDo.getMaintainers());

        List<String> sortedTags = SortUtil.sortTags(tags);
        opVo.setTags(sortedTags);

        Map<String, String> pkgIdsMap = new HashMap<>();
        for (Map.Entry<String, Object> pkgId : pkgIds.entrySet()) {
            String key = pkgId.getKey();
            String value = (String) pkgId.getValue();
            pkgIdsMap.put(key, value);
        }
        opVo.setPkgIds(pkgIdsMap);

        Map<String, String> maintainersMap = maintainers.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getKey, e -> String.valueOf(e.getValue())
        ));
        opVo.setMaintainers(maintainersMap);
        return opVo;
    }

    /**
     * Extracts a specific column from a list of FieldApplicationDO objects and
     * returns it as a list of strings.
     *
     * @param columnList The list of FieldApplicationDO objects.
     * @param column     The name of the column to extract.
     * @return A list of strings representing the extracted column values.
     */
    public static List<String> toColumn(final List<FieldApplicationDO> columnList, final String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = FieldApplicationDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (FieldApplicationDO pkg : columnList) {
                if (pkg == null) {
                    continue;
                }
                Object obj = field.get(pkg);

                if (obj == null) {
                    LOGGER.warn("Field value is null for pkg: {}", pkg);
                    continue;
                }

                if (!(obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(pkg);
                res.add(value);
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e.getMessage());
        }
        return res;
    }

    /**
     * Converts a FiledApplicationSearchCondition object to an
     * ApplicationPackageSearchCondition object.
     *
     * @param con The FiledApplicationSearchCondition object to convert.
     * @return The converted ApplicationPackageSearchCondition object.
     */
    public static ApplicationPackageSearchCondition toApp(final FiledApplicationSerachCondition con) {
        ApplicationPackageSearchCondition appCon = new ApplicationPackageSearchCondition();
        BeanUtils.copyProperties(con, appCon);
        appCon.setName("");
        appCon.setTimeOrder("");
        return appCon;
    }

    /**
     * Converts a FieldApplicationSearchCondition object to an
     * RPMPackageSearchCondition object.
     *
     * @param con The FieldApplicationSearchCondition object to convert.
     * @return The converted RPMPackageSearchCondition object.
     */
    public static RPMPackageSearchCondition toRpm(final FiledApplicationSerachCondition con) {
        RPMPackageSearchCondition rPMCon = new RPMPackageSearchCondition();
        BeanUtils.copyProperties(con, rPMCon);
        rPMCon.setName("");
        return rPMCon;
    }

    /**
     * Converts a FieldApplicationSearchCondition object to an
     * OEPackageSearchCondition object.
     *
     * @param con The FieldApplicationSearchCondition object to convert.
     * @return The converted OEPackageSearchCondition object.
     */
    public static OEPackageSearchCondition toOep(final FiledApplicationSerachCondition con) {
        OEPackageSearchCondition oepCon = new OEPackageSearchCondition();
        BeanUtils.copyProperties(con, oepCon);
        oepCon.setName("");
        return oepCon;
    }

    /**
     * Converts a FieldApplicationSearchCondition object to an
     * EPKGPackageSearchCondition object.
     *
     * @param con The FieldApplicationSearchCondition object to convert.
     * @return The converted EPKGPackageSearchCondition object.
     */
    public static EPKGPackageSearchCondition toEpkg(final FiledApplicationSerachCondition con) {
        EPKGPackageSearchCondition eCon = new EPKGPackageSearchCondition();
        BeanUtils.copyProperties(con, eCon);
        eCon.setName("");
        return eCon;
    }

    /**
     * Converts a FieldApplicationSearchCondition object to an
     * FieldPkgSearchCondition object.
     *
     * @param con The FiledApplicationSerachCondition object to convert.
     * @return The converted FieldPkgSearchCondition object.
     */
    public static FieldPkgSearchCondition toFieldPkg(final FiledApplicationSerachCondition con) {
        FieldPkgSearchCondition fCon = new FieldPkgSearchCondition();
        BeanUtils.copyProperties(con, fCon);
        fCon.setName("");
        return fCon;
    }

    /**
     * convert map to OsArchNumVO.
     * @param mapList list of maps.
     * @return list of OsArchNumVO.
     */
    public static List<OsArchNumVO> toOsArchNumVO(List<Map<String, Object>> mapList) {
        if (mapList == null || mapList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Field> fList = FieldUtils.getAllFieldsList(OsArchNumVO.class);
        List<OsArchNumVO> vList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            vList.add(toOsArchNumVO(fList, map));
        }
        return vList.stream().filter(p -> p != null).collect(Collectors.toList());
    }

    /**
     * convert map to OsArchNumVO.
     * @param fList fields of OsArchNumVO.
     * @param map map.
     * @return OsArchNumVO.
     */
    public static OsArchNumVO toOsArchNumVO(List<Field> fList, Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        String j = ObjectMapperUtil.writeValueAsString(map);
        return ObjectMapperUtil.jsonToObject(j, OsArchNumVO.class);
    }
}
