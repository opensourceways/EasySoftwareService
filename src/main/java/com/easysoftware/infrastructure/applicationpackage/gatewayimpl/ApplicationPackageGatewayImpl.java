package com.easysoftware.infrastructure.applicationpackage.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageEulerArchsVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageEulerVersionVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageTagsVo;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConverter;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.mapper.ApplicationPackageDOMapper;
import com.power.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationPackageGatewayImpl implements ApplicationPackageGateway {
    /**
     * Autowired ApplicationPackageDOMapper bean.
     */
    @Autowired
    private ApplicationPackageDOMapper appPkgMapper;

    /**
     * Value for the application package icon path.
     */
    @Value("${apppkg.icon.path}")
    private String apppkgIconPath;

    /**
     * Check if an application exists based on its name.
     *
     * @param name The name of the application
     * @return true if the application exists, false otherwise
     */
    @Override
    public boolean existApp(final String name) {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.eq("name", name);
        }
        return appPkgMapper.exists(wrapper);
    }

    /**
     * Query menu items based on the provided search condition.
     *
     * @param condition The search condition for querying menu items
     * @return A map containing menu items
     */
    @Override
    public Map<String, Object> queryMenuByName(final ApplicationPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<ApplicationPackageDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<ApplicationPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationPackageDO(),
                condition, null);
        ApplicationPackageMenuVo pkgVo = new ApplicationPackageMenuVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);

        IPage<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        long total = resPage.getTotal();
        if (total == 0) {
            throw new NoneResException("the image package does not exist");
        }

        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        List<ApplicationPackageMenuVo> menus = ApplicationPackageConverter.toMenu(appDOs);
        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", menus);
        return res;
    }

    /**
     * Query tags based on the provided search condition.
     *
     * @param condition The search condition for querying tags
     * @return A map containing tags information
     */
    @Override
    public Map<String, Object> queryTagsByName(final ApplicationPackageSearchCondition condition) {
        Page<ApplicationPackageDO> page = createPage(condition);
        QueryWrapper<ApplicationPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationPackageDO(),
                condition, null);
        IPage<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        List<ApplicationPackageTagsVo> aggregatePkgs = ApplicationPackageConverter.aggregateByTags(appDOs);
        long total = aggregatePkgs.size();

        if (total == 0) {
            throw new NoneResException("the tag does not exist");
        }

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", aggregatePkgs));
        return res;
    }

    /**
     * Query detailed information based on the provided search condition.
     *
     * @param condition The search condition for querying detailed information
     * @return A map containing detailed information
     */
    @Override
    public Map<String, Object> queryDetailByName(final ApplicationPackageSearchCondition condition) {
        Page<ApplicationPackageDO> page = createPage(condition);
        QueryWrapper<ApplicationPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationPackageDO(),
                condition, null);

        IPage<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        List<ApplicationPackageDetailVo> appDetails = ApplicationPackageConverter.toDetail(appDOs);
        long total = resPage.getTotal();


        if (total == 0) {
            throw new NoneResException("the image package does not exist");
        }

        return Map.ofEntries(
                Map.entry("total", total),
                Map.entry("list", appDetails));
    }

    /**
     * Create a page of ApplicationPackageDO objects based on the provided search
     * condition.
     *
     * @param condition The search condition for creating the page
     * @return A page of ApplicationPackageDO objects
     */
    private Page<ApplicationPackageDO> createPage(final ApplicationPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        return new Page<>(pageNum, pageSize);
    }

    /**
     * Get the total number of records in the table.
     *
     * @return The total number of records in the table
     */
    @Override
    public long queryTableLength() {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct name");
        return appPkgMapper.selectCount(wrapper);
    }

    /**
     * Select a single ApplicationPackageMenuVo object by name.
     *
     * @param name The name used to select the object
     * @return The selected ApplicationPackageMenuVo object
     */
    @Override
    public ApplicationPackageMenuVo selectOne(final String name) {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("pkg_id");
        if (name != null) {
            wrapper.eq("name", name);
        }
        wrapper.last("limit 1");
        List<ApplicationPackageDO> appList = appPkgMapper.selectList(wrapper);
        if (appList.size() == 0) {
            return new ApplicationPackageMenuVo();
        }
        return ApplicationPackageConverter.toMenu(appList.get(0));
    }

    /**
     * Query detailed information by package ID.
     *
     * @param pkgId The package ID to query detailed information
     * @return A list of ApplicationPackageDetailVo objects
     */
    @Override
    public List<ApplicationPackageDetailVo> queryDetailByPkgId(final String pkgId) {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        if (pkgId != null) {
            wrapper.eq("pkg_id", pkgId);
        }
        List<ApplicationPackageDO> appList = appPkgMapper.selectList(wrapper);
        return ApplicationPackageConverter.toDetail(appList);
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
     * Query a specific column in the database and return the results as a list of
     * strings.
     *
     * @param column The column to query
     * @return A list of strings containing the query results
     */
    public List<String> queryColumn(final String column) {
        // 白名单列
        List<String> allowedColumns = Arrays.asList("category", "os", "arch");

        if (!allowedColumns.contains(column)) {
            throw new ParamErrorException("Unsupported column");
        }

        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        // 安全地选择列，列名已经通过白名单验证
        wrapper.select("distinct " + column);
        List<ApplicationPackageDO> rpmColumn = new ArrayList<>();
        try {
            rpmColumn = appPkgMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("unsupported param");
        }

        String underlineToCamelColumn = StringUtil.underlineToCamel(column);

        return ApplicationPackageConverter.toColumn(rpmColumn, underlineToCamelColumn);
    }

    /**
     * Query the Euler Version based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the appkg Euler
     *                  Version
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerVersionByName(final ApplicationPackageSearchCondition condition) {
        QueryWrapper<ApplicationPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationPackageDO(),
                condition, "");
        ApplicationPackageEulerVersionVo pkgVo = new ApplicationPackageEulerVersionVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        if (condition.getName() != null) {
            wrapper.eq("name", condition.getName());
        }
        wrapper.select(columns);
        wrapper.groupBy("os", "arch");
        List<ApplicationPackageDO> appkgList = appPkgMapper.selectList(wrapper);
        List<ApplicationPackageEulerVersionVo> versions = ApplicationPackageConverter.toVersion(appkgList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", versions.size()),
                Map.entry("list", versions));
        return res;
    }

    /**
     * Query the Euler archs based on the provided search condition.
     *
     * @param condition The search condition for querying a part of the appkg Euler
     *                  archs
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryEulerArchsByName(final ApplicationPackageSearchCondition condition) {
        QueryWrapper<ApplicationPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationPackageDO(),
                condition, "");
        ApplicationPackageEulerArchsVo pkgVo = new ApplicationPackageEulerArchsVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        if (condition.getName() != null) {
            wrapper.eq("name", condition.getName());
        }
        wrapper.select(columns);
        wrapper.groupBy("arch");
        List<ApplicationPackageDO> appkgList = appPkgMapper.selectList(wrapper);
        List<ApplicationPackageEulerArchsVo> versions = ApplicationPackageConverter.toArchs(appkgList);
        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", versions.size()),
                Map.entry("list", versions));
        return res;
    }
}
