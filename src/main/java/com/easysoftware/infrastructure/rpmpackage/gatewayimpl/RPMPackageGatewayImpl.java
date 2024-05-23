package com.easysoftware.infrastructure.rpmpackage.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageEulerArchsVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageEulerVersionVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
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
public class RPMPackageGatewayImpl implements RPMPackageGateway {

    /**
     * Autowired RPMPackageDOMapper for database operations.
     */
    @Autowired
    private RPMPackageDOMapper rPMPkgMapper;

    /**
     * Autowired ObjectMapper for JSON processing.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Logger for RPMPackageGatewayImpl class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RPMPackageGatewayImpl.class);

    /**
     * Check if an RPM package exists based on its unique identifier.
     *
     * @param unique The unique identifier of the RPM package
     * @return true if the RPM package exists, false otherwise
     */
    @Override
    public boolean existRPM(final RPMPackageUnique unique) {
        Map<String, Object> map = objectMapper.convertValue(unique, HashMap.class);

        Map<String, Object> underlineMap = new HashMap<>();
        for (String key : map.keySet()) {
            String underlineKey = StringUtil.camelToUnderline(key);
            underlineMap.put(underlineKey, map.get(key));
        }

        QueryWrapper<RPMPackageDO> wrapper = Wrappers.query();
        wrapper.setEntityClass(RPMPackageDO.class);
        wrapper.allEq(underlineMap, false);
        return rPMPkgMapper.exists(wrapper);
    }

    /**
     * Query detailed information based on the provided search condition for RPM
     * packages.
     *
     * @param condition The search condition for querying RPM package details
     * @return A map containing detailed information
     */
    @Override
    public Map<String, Object> queryDetailByName(final RPMPackageSearchCondition condition) {
        Page<RPMPackageDO> page = createPage(condition);
        QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(),
                condition, "rpm_update_at");
        IPage<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
        List<RPMPackageDO> rPMDOs = resPage.getRecords();
        List<RPMPackageDetailVo> rPMDetails = RPMPackageConverter.toDetail(rPMDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", rPMDetails));

        return res;
    }

    /**
     * Check if an RPM package exists based on its ID.
     *
     * @param id The ID of the RPM package
     * @return true if the RPM package exists, false otherwise
     */
    @Override
    public boolean existRPM(final String id) {
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return rPMPkgMapper.exists(wrapper);
    }

    /**
     * Query menu items based on the provided search condition for RPM packages.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    @Override
    public Map<String, Object> queryMenuByName(final RPMPackageSearchCondition condition) {
        Page<RPMPackageDO> page = createPage(condition);
        QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(),
                condition, "rpm_update_at");
        RPMPackageMenuVo pkgVo = new RPMPackageMenuVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);
        IPage<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
        List<RPMPackageDO> rpmDOs = resPage.getRecords();
        List<RPMPackageMenuVo> rPMMenus = RPMPackageConverter.toMenu(rpmDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", rPMMenus));
        return res;
    }

    /**
     * Creates a Page of RPMPackageDO based on the provided search condition.
     *
     * @param condition The RPMPackageSearchCondition object to create the page
     *                  from.
     * @return A Page of RPMPackageDO entities.
     */
    private Page<RPMPackageDO> createPage(final RPMPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        return new Page<>(pageNum, pageSize);
    }

    /**
     * Query columns based on the provided list of columns for RPM packages.
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

        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<RPMPackageDO> rpmColumn = new ArrayList<>();
        try {
            rpmColumn = rPMPkgMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("unsupported param");
        }

        String underlineToCamelColumn = StringUtil.underlineToCamel(column);

        return RPMPackageConverter.toColumn(rpmColumn, underlineToCamelColumn);
    }

    /**
     * Get the total number of records in the RPM package table.
     *
     * @return The total number of records in the table
     */
    @Override
    public long queryTableLength() {
        return rPMPkgMapper.selectCount(null);
    }

    /**
     * Query part of the RPM package menu based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the RPM package
     *                  menu
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryPartRPMPkgMenu(final RPMPackageSearchCondition condition) {
        QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(),
                condition, "");
        RPMPackageDomainVo pkgVo = new RPMPackageDomainVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);
        wrapper.in("category", List.of("AI", "大数据", "分布式存储", "数据库", "云服务", "HPC"));
        List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
        List<RPMPackageDomainVo> menus = RPMPackageConverter.toDomain(rpmList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", menus.size()),
                Map.entry("list", menus));
        return res;
    }

    /**
     * Query the Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the RPM Euler
     *                  Version
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerVersionByName(final RPMPackageSearchCondition condition) {
        QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(),
                condition, "");
        RPMPackageEulerVersionVo pkgVo = new RPMPackageEulerVersionVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.eq("name", condition.getName())
                .select(columns)
                .groupBy("os", "arch");
        List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
        List<RPMPackageEulerVersionVo> versions = RPMPackageConverter.toVersion(rpmList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", versions.size()),
                Map.entry("list", versions));
        return res;
    }

    /**
     * Query the Euler archs based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the RPM Euler
     *                  archs
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerArchsByName(final RPMPackageSearchCondition condition) {
        QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(),
                condition, "");
        RPMPackageEulerArchsVo pkgVo = new RPMPackageEulerArchsVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.eq("name", condition.getName())
                .select(columns)
                .groupBy("arch");
        List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
        List<RPMPackageEulerArchsVo> versions = RPMPackageConverter.toArchs(rpmList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", versions.size()),
                Map.entry("list", versions));
        return res;
    }

    /**
     * Select a single RPMPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected RPMPackageMenuVo object
     */
    @Override
    public RPMPackageMenuVo selectOne(final String name) {
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        wrapper.select("pkg_id");
        wrapper.last("order by rpm_update_at desc limit 1");
        List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
        if (rpmList.size() == 0) {
            return new RPMPackageMenuVo();
        }
        return RPMPackageConverter.toMenu(rpmList.get(0));
    }

    /**
     * Query detailed information by package ID for RPM packages.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of RPMPackageDetailVo objects
     */
    @Override
    public List<RPMPackageDetailVo> queryDetailByPkgId(final String pkgId) {
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("pkg_id", pkgId);
        List<RPMPackageDO> rpmList = rPMPkgMapper.selectList(wrapper);
        return RPMPackageConverter.toDetail(rpmList);
    }
}
