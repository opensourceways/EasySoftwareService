package com.easysoftware.infrastructure.applicationversion.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.converter.ApplicationVersionConvertor;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import com.easysoftware.infrastructure.mapper.ApplicationVersionDOMapper;
import com.power.common.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationVersionGatewayImpl implements ApplicationVersionGateway {

    /**
     * Autowired field for the ApplicationVersionDOMapper.
     */
    @Autowired
    private ApplicationVersionDOMapper appVersionMapper;

    /**
     * Delete an application by name.
     *
     * @param name The name of the application to delete
     * @return true if the delete operation was successful, false otherwise
     */
    @Override
    public boolean delete(final String name) {
        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper.in("name", name);
        int mark = appVersionMapper.delete(wrapper);
        return mark == 1;
    }

    /**
     * Check if an application exists based on its name.
     *
     * @param name The name of the application
     * @return true if the application exists, false otherwise
     */
    @Override
    public boolean existApp(final String name) {
        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return appVersionMapper.exists(wrapper);
    }

    /**
     * Save an ApplicationVersion object.
     *
     * @param appVersion The ApplicationVersion object to save
     * @return true if the save operation was successful, false otherwise
     */
    @Override
    public boolean save(final ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = ApplicationVersionConvertor.toDataObjectForCreate(appVersion);
        int mark = appVersionMapper.insert(appVersionDO);
        return mark == 1;
    }

    /**
     * Update an existing ApplicationVersion object.
     *
     * @param appVersion The ApplicationVersion object to update
     * @return true if the update operation was successful, false otherwise
     */
    @Override
    public boolean update(final ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = ApplicationVersionConvertor.toDataObjectForUpdate(appVersion);

        UpdateWrapper<ApplicationVersionDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", appVersion.getName());

        int mark = appVersionMapper.update(appVersionDO, wrapper);
        return mark == 1;
    }

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryByName(final ApplicationVersionSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String name = condition.getName();

        Page<ApplicationVersionDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper = name == null ? null : wrapper.eq("name", name);

        Page<ApplicationVersionDO> resPage = appVersionMapper.selectPage(page, wrapper);
        List<ApplicationVersionDO> appDOs = resPage.getRecords();
        List<ApplicationVersion> appDetails = ApplicationVersionConvertor.toEntity(appDOs);

        Map<String, Object> res = Map.ofEntries(
                Map.entry("total", resPage.getTotal()),
                Map.entry("list", appDetails));

        return res;
    }

    /**
     * Convert a batch of data objects to ApplicationVersionDO objects.
     *
     * @param dataObject A collection of data objects to convert
     * @return A collection of ApplicationVersionDO objects
     */
    @Override
    public Collection<ApplicationVersionDO> convertBatch(final Collection<String> dataObject) {
        Collection<ApplicationVersionDO> objList = new ArrayList<>();
        for (String obj : dataObject) {
            ApplicationVersion appVer = ObjectMapperUtil.jsonToObject(obj, ApplicationVersion.class);
            ApplicationVersionDO appVersionDO = ApplicationVersionConvertor.toDataObjectForCreate(appVer);
            objList.add(appVersionDO);
        }
        return objList;
    }

    /**
     * Query information based on the provided search condition.
     *
     * @param condition The search condition for querying application versions
     * @return A map containing relevant information
     */
    @Override
    public Map<String, Object> queryByEulerOsVersion(ApplicationVersionSearchCondition condition) {
        Page<ApplicationVersionDO> page = this.<ApplicationVersionDO>createPage(condition);

        QueryWrapper<ApplicationVersionDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationVersionDO(),
                condition, "");

        Page<ApplicationVersionDO> resPage = appVersionMapper.selectPage(page, wrapper);
        List<ApplicationVersionDO> appList = resPage.getRecords();
        List<ApplicationVersion> appDetails = ApplicationVersionConvertor.toEntity(appList);
        return Map.ofEntries(
                Map.entry("total", resPage.getTotal()),
                Map.entry("list", appDetails));
    }

    private <T> Page<T> createPage(ApplicationVersionSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        return new Page<T>(pageNum, pageSize);
    }

    /**
     * Query column.
     *
     * @param columns columns
     * @return A map containing relevant information
     */
    @Override
    public Map<String, List<String>> queryColumn(List<String> columns) {
        Map<String, List<String>> res = new HashMap<>();
        for (String column : columns) {
            List<String> colList = queryColumn(column);
            if ("eulerOsVersion".equals(column)) {
                colList = QueryWrapperUtil.sortOsColumn(colList);
            }
            res.put(column, colList);
        }
        return res;
    }

    private List<String> queryColumn(String column) {
        List<String> allowedColumns = Arrays.asList("eulerOsVersion");
        if (!allowedColumns.contains(column)) {
            throw new ParamErrorException("Unsupported column");
        }

        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct " + StringUtil.camelToUnderline(column));
        List<ApplicationVersionDO> columnList = new ArrayList<>();
        try {
            columnList = appVersionMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("unsupported param");
        }

        String underlineToCamelColumn = StringUtil.underlineToCamel(column);
        return ApplicationVersionConvertor.toColumn(columnList, underlineToCamelColumn);
    }
}
