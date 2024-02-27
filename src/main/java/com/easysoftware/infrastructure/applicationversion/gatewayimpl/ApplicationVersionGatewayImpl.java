package com.easysoftware.infrastructure.applicationversion.gatewayimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
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
    public boolean delete(List<String> names) {
        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        wrapper.in("name", names);
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
    public List<ApplicationVersion> queryByName(ApplicationVersionSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String name = condition.getName();

        Page<ApplicationVersionDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<ApplicationVersionDO> wrapper = new QueryWrapper<>();
        if ("all".equals(name)) {
            wrapper = null;
        } else {
            wrapper.eq("name", name);
        }
        
        Page<ApplicationVersionDO> resPage = appVersionMapper.selectPage(page, wrapper);
        List<ApplicationVersionDO> appDOs = resPage.getRecords();
        List<ApplicationVersion> res = ApplicationVersionConvertor.toEntity(appDOs);
        
        return res;
    }
}


