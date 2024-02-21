package com.easysoftware.infrastructure.applicationpackage.gatewayimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConvertor;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.mapper.ApplicationPackageDOMapper;

@Component
public class ApplicationPackageGatewayImpl implements ApplicationPackageGateway {
    @Autowired
    private ApplicationPackageDOMapper appPkgMapper;

    @Override
    public boolean delete(String name) {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        int mark = appPkgMapper.delete(wrapper);
        return mark == 1;
    }


    @Override
    public boolean existApp(String name) {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return appPkgMapper.exists(wrapper);
    }


    @Override
    public boolean save(ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = ApplicationPackageConvertor.toDataObjectForCreate(appPkg);
        int mark = appPkgMapper.insert(appPkgDO);
        return mark == 1;
    }


    @Override
    public boolean update(ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = ApplicationPackageConvertor.toDataObjectForUpdate(appPkg);

        UpdateWrapper<ApplicationPackageDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", appPkg.getName());

        int mark = appPkgMapper.update(appPkgDO, wrapper);
        return mark == 1;
    }

    @Override
    public List<ApplicationPackage> queryByName(ApplicationPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String name = condition.getName();

        Page<ApplicationPackageDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        if ("all".equals(name)) {
        } else {
            wrapper.eq("name", name);
        }
        
        Page<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        List<ApplicationPackage> res = ApplicationPackageConvertor.toEntity(appDOs);
        
        return res;
    }
}


