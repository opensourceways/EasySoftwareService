package com.easysoftware.infrastructure.applicationversion.gatewayimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

@Component
public class ApplicationVersionGatewayImpl implements ApplicationVersionGateway {
    @Autowired
    private ApplicationVersionDOMapper appVersionMapper;

    @Override
    public boolean delete(String name) {
        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper.in("name", name);
        int mark = appVersionMapper.delete(wrapper);
        return mark == 1;
    }


    @Override
    public boolean existApp(String name) {
        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return appVersionMapper.exists(wrapper);
    }


    @Override
    public boolean save(ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = ApplicationVersionConvertor.toDataObjectForCreate(appVersion);
        int mark = appVersionMapper.insert(appVersionDO);
        return mark == 1;
    }


    @Override
    public boolean update(ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = ApplicationVersionConvertor.toDataObjectForUpdate(appVersion);

        UpdateWrapper<ApplicationVersionDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", appVersion.getName());

        int mark = appVersionMapper.update(appVersionDO, wrapper);
        return mark == 1;
    }

    @Override
    public Map<String, Object> queryByName(ApplicationVersionSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String name = condition.getName();

        Page<ApplicationVersionDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper = name == null ? null :  wrapper.eq("name", name);

        Page<ApplicationVersionDO> resPage = appVersionMapper.selectPage(page, wrapper);
        List<ApplicationVersionDO> appDOs = resPage.getRecords();
        List<ApplicationVersion> appDetails = ApplicationVersionConvertor.toEntity(appDOs);

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", resPage.getTotal()),
            Map.entry("list", appDetails)
        );
        
        return res;
    }

    @Override
    public Collection<ApplicationVersionDO> convertBatch(Collection<String> dataObject){
        Collection<ApplicationVersionDO> ObjList = new ArrayList<>();
        for (String obj : dataObject) {
            ApplicationVersion appVer = ObjectMapperUtil.jsonToObject(obj, ApplicationVersion.class);
            ApplicationVersionDO appVersionDO = ApplicationVersionConvertor.toDataObjectForCreate(appVer);
            ObjList.add(appVersionDO);
        }
        return ObjList;
    }
}


