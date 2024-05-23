package com.easysoftware.infrastructure.applicationversion.gatewayimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.converter.ApplicationVersionConvertor;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import com.easysoftware.infrastructure.mapper.ApplicationVersionDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
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
                Map.entry("list", appDetails)
        );

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
}


