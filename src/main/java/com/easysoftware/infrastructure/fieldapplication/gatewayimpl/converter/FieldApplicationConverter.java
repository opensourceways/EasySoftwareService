package com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.fieldpkg.dto.FieldPkgSearchCondition;
import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.dto.InputFiledApplication;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject.FieldApplicationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * Converts an InputFieldApplication object to a FieldApplicationDO data object.
     *
     * @param input The InputFieldApplication object to convert.
     * @return The converted FieldApplicationDO data object.
     */
    public static FieldApplicationDO toDataObject(final InputFiledApplication input) {
        FieldApplicationDO fieldApplicationDO = new FieldApplicationDO();
        BeanUtils.copyProperties(input, fieldApplicationDO);
        return fieldApplicationDO;
    }

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
        opDo.getTags();
        List<String> tags = ObjectMapperUtil.toObjectList(String.class, opDo.getTags());
        Map<String, Object> pkgIds = ObjectMapperUtil.toMap(opDo.getPkgIds());

        Set<String> tagsSet = new HashSet<>();
        tagsSet.addAll(tags);
        opVo.setTags(tagsSet);

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
            LOGGER.error(MessageCode.EC00011.getMsgEn(), e);
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
}
