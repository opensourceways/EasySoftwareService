package com.easysoftware.infrastructure.epkgpackage.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageEulerArchsVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageEulerVersionVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter.EPKGPackageConverter;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EPKGPackageGatewayImpl implements EPKGPackageGateway {

    /**
     * Logger instance for EPKGPackageGatewayImpl.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EPKGPackageGatewayImpl.class);

    /**
     * Autowired EPKGPackageDOMapper for database operations.
     */
    @Autowired
    private EPKGPackageDOMapper ePKGPkgMapper;

    /**
     * Autowired ObjectMapper for JSON serialization/deserialization.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Check if an EPKG package exists based on its unique identifier.
     *
     * @param unique The unique identifier of the EPKG package
     * @return true if the EPKG package exists, false otherwise
     */
    @Override
    public boolean existEPKG(final EPKGPackageUnique unique) {
        Map<String, Object> map = objectMapper.convertValue(unique, HashMap.class);

        Map<String, Object> underlineMap = new HashMap<>();
        for (String key : map.keySet()) {
            String underlineKey = StringUtil.camelToUnderline(key);
            underlineMap.put(underlineKey, map.get(key));
        }

        QueryWrapper<EPKGPackageDO> wrapper = Wrappers.query();
        wrapper.setEntityClass(EPKGPackageDO.class);
        wrapper.allEq(underlineMap, false);
        return ePKGPkgMapper.exists(wrapper);
    }

    /**
     * Check if an EPKG package exists based on its ID.
     *
     * @param id The ID of the EPKG package
     * @return true if the EPKG package exists, false otherwise
     */
    @Override
    public boolean existEPKG(final String id) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        if (id != null) {
            wrapper.eq("id", id);
        }
        return ePKGPkgMapper.exists(wrapper);
    }

    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying EPKG package details
     * @return A map containing detailed information
     */
    @Override
    public Map<String, Object> queryDetailByName(final EPKGPackageSearchCondition condition) {
        Page<EPKGPackageDO> page = createPage(condition);
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(),
                condition, "epkg_update_at");
        IPage<EPKGPackageDO> resPage = ePKGPkgMapper.selectPage(page, wrapper);
        List<EPKGPackageDO> rPMDOs = resPage.getRecords();
        List<EPKGPackageDetailVo> rPMDetails = EPKGPackageConverter.toDetail(rPMDOs);
        long total = resPage.getTotal();

        if (total == 0) {
            throw new NoneResException("the epkg package does not exist");
        }

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", rPMDetails));

        return res;
    }

    /**
     * Query menu items based on the provided search condition.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    @Override
    public Map<String, Object> queryMenuByName(final EPKGPackageSearchCondition condition) {
        Page<EPKGPackageDO> page = createPage(condition);
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(),
                condition, "epkg_update_at");
        EPKGPackageMenuVo pkgVo = new EPKGPackageMenuVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);
        IPage<EPKGPackageDO> resPage = ePKGPkgMapper.selectPage(page, wrapper);
        List<EPKGPackageDO> rpmDOs = resPage.getRecords();
        List<EPKGPackageMenuVo> rPMMenus = EPKGPackageConverter.toMenu(rpmDOs);
        long total = resPage.getTotal();

        if (total == 0) {
            throw new NoneResException("the epkg package does not exist");
        }

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", rPMMenus));
        return res;
    }

    /**
     * Creates a Page of EPKGPackageDO based on the provided search condition.
     *
     * @param condition The EPKGPackageSearchCondition object to create the page
     *                  from.
     * @return A Page of EPKGPackageDO entities.
     */
    private Page<EPKGPackageDO> createPage(final EPKGPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<EPKGPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    /**
     * Query columns based on the provided list of columns.
     *
     * @param columns The list of columns to query
     * @return A map containing column data
     */
    @Override
    public Map<String, List<String>> queryColumn(final List<String> columns) {
        Map<String, List<String>> res = new HashMap<>();
        for (String column : columns) {
            List<String> colList = queryColumn(column);
            if ("os".equals(column)) {
                colList = QueryWrapperUtil.sortOsColumn(colList);
            }
            if ("category".equals(column)) {
                colList = QueryWrapperUtil.sortCategoryColumn(colList);
            }
            res.put(column, colList);
        }
        return res;
    }

    /**
     * Query a specific column and return the results as a list of strings.
     *
     * @param column The name of the column to query.
     * @return A list of strings representing the queried column.
     */
    public List<String> queryColumn(final String column) {
        // 白名单列
        List<String> allowedColumns = Arrays.asList("category", "os", "arch");

        if (!allowedColumns.contains(column)) {
            throw new ParamErrorException("Unsupported column");
        }

        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<EPKGPackageDO> rpmColumn = new ArrayList<>();
        try {
            rpmColumn = ePKGPkgMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("unsupported param");
        }
        String underlineToCamelColumn = StringUtil.underlineToCamel(column);

        return EPKGPackageConverter.toColumn(rpmColumn, underlineToCamelColumn);
    }

    /**
     * Get the total number of records in the table.
     *
     * @return The total number of records in the table
     */
    @Override
    public long queryTableLength() {
        return ePKGPkgMapper.selectCount(null);
    }

    /**
     * Select a single EPKGPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected EPKGPackageMenuVo object
     */
    @Override
    public EPKGPackageMenuVo selectOne(final String name) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("pkg_id");
        if (name != null) {
            wrapper.eq("name", name);
        }
        wrapper.last("order by epkg_update_at desc limit 1");
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        if (epkgList.size() == 0) {
            return new EPKGPackageMenuVo();
        }
        return EPKGPackageConverter.toMenu(epkgList.get(0));
    }

    /**
     * Query detailed information by package ID.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of EPKGPackageDetailVo objects
     */
    @Override
    public List<EPKGPackageDetailVo> queryDetailByPkgId(final String pkgId) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("pkg_id", pkgId);
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        return EPKGPackageConverter.toDetail(epkgList);
    }

    /**
     * Query the Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the epkg Euler
     *                  Version
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerVersionByName(final EPKGPackageSearchCondition condition) {
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(),
                condition, "");
        EPKGPackageEulerVersionVo pkgVo = new EPKGPackageEulerVersionVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        if (condition.getName() != null) {
            wrapper.eq("name", condition.getName());
        }
        wrapper.select(columns);
        wrapper.groupBy("os", "arch");
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        List<EPKGPackageEulerVersionVo> versions = EPKGPackageConverter.toVersion(epkgList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", versions.size()),
                Map.entry("list", versions));
        return res;
    }

    /**
     * Query the Euler archs based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the epkg Euler
     *                  archs
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerArchsByName(final EPKGPackageSearchCondition condition) {
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(),
                condition, "");
        EPKGPackageEulerArchsVo pkgVo = new EPKGPackageEulerArchsVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        if (condition.getName() != null) {
            wrapper.eq("name", condition.getName());
        }
        wrapper.select(columns);
        wrapper.groupBy("arch");
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        List<EPKGPackageEulerArchsVo> versions = EPKGPackageConverter.toArchs(epkgList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", versions.size()),
                Map.entry("list", versions));
        return res;
    }

}
