package com.easysoftware.infrastructure.fieldpkg.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.easysoftware.application.fieldpkg.vo.FieldPkgVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.SortUtil;
import com.easysoftware.infrastructure.fieldpkg.dataobject.FieldPkgDO;

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
     * Converts a FieldApplicationDO object to a FieldPkgDO view object.
     *
     * @param opDo The FieldApplicationDO object to convert.
     * @return The converted FieldApplicationVo view object.
     */
    public static FieldPkgVo toVo(final FieldPkgDO opDo) {
        FieldPkgVo opVo = new FieldPkgVo();
        BeanUtils.copyProperties(opDo, opVo);
        opDo.getTags();
        List<String> tags = ObjectMapperUtil.toObjectList(String.class, opDo.getTags());
        Map<String, Object> pkgIds = ObjectMapperUtil.toMap(opDo.getPkgIds());

        List<String> sortedTags = SortUtil.sortTags(tags);
        opVo.setTags(sortedTags);

        Map<String, String> pkgIdsMap = new HashMap<>();
        for (Map.Entry<String, Object> pkgId : pkgIds.entrySet()) {
            String key = pkgId.getKey();
            String value = (String) pkgId.getValue();
            pkgIdsMap.put(key, value);
        }
        opVo.setPkgIds(pkgIdsMap);
        return opVo;
    }

    /**
     * Extracts a specific column from a list of FieldApplicationDO objects and returns it as a list of strings.
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
                if (!(obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(pkg);
                res.add(value);
            }
        } catch (Exception e) {
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e);
        }
        return res;
    }
}
