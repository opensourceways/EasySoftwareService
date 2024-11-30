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

package com.easysoftware.infrastructure.fieldpkg.converter;

import com.easysoftware.application.fieldpkg.vo.FieldPkgVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.infrastructure.fieldpkg.dataobject.FieldPkgDO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class FieldPkgConverter {
    // Private constructor to prevent instantiation of the utility class
    private FieldPkgConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate FieldApplicationConverter class");
    }

    /**
     * Logger instance for FieldPkgConverter.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldPkgConverter.class);

    /**
     * Converts a list of FieldPkgDO objects to a list of FieldPkgDO view objects.
     *
     * @param doList The list of FieldPkgDO objects to convert.
     * @return A list of FieldPkgVo view objects.
     */
    public static List<FieldPkgVo> toVo(final List<FieldPkgDO> doList) {
        List<FieldPkgVo> res = new ArrayList<>();
        for (FieldPkgDO op : doList) {
            FieldPkgVo vo = toVo(op);
            res.add(vo);
        }
        return res;
    }

    /**
     * aggregate a list of FieldPkgVo objects to a list of aggregateFieldPkgVo view
     * objects.
     *
     * @param doList The list of FieldPkgVo objects to aggregate.
     * @return A list of FieldPkgVo view objects.
     */
    public static List<FieldPkgVo> aggregateVoByName(final List<FieldPkgVo> doList) {
        List<FieldPkgVo> res = new ArrayList<>();
        Map<String, FieldPkgVo> groupedByName = new HashMap<>();
        for (FieldPkgVo fieldPkgVo : doList) {
            if (!groupedByName.containsKey(fieldPkgVo.getName())) {
                groupedByName.put(fieldPkgVo.getName(), fieldPkgVo);
                res.add(fieldPkgVo);
            }
        }
        return res;
    }

    /**
     * Converts a FieldApplicationDO object to a FieldPkgDO view object.
     *
     * @param opDo The FieldApplicationDO object to convert.
     * @return The converted FieldApplicationVo view object.
     */
    public static FieldPkgVo toVo(final FieldPkgDO opDo) {
        FieldPkgVo opVo = new FieldPkgVo();
        BeanUtils.copyProperties(opDo, opVo);
        List<String> tags = ObjectMapperUtil.toObjectList(String.class, opDo.getTags());
        Map<String, Object> pkgIds = ObjectMapperUtil.toMap(opDo.getPkgIds());
        Map<String, Object> maintainers = ObjectMapperUtil.toMap(opDo.getMaintainers());

        List<String> sortedTags = SortUtil.sortTags(tags);
        opVo.setTags(sortedTags);

        Map<String, String> pkgIdsMap = new HashMap<>();
        pkgIds.forEach((key, value) -> pkgIdsMap.put(key, String.valueOf(value)));
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
    public static List<String> toColumn(final List<FieldPkgDO> columnList, final String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = FieldPkgDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (FieldPkgDO pkg : columnList) {
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
        return res.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }
}
