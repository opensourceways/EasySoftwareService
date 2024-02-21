package com.easysoftware.infrastructure.rpmpackage.gatewayimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConvertor;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

@Component
public class RPMPackageGatewayImpl implements RPMPackageGateway {
    @Autowired
    private RPMPackageDOMapper rPMPkgMapper;


    @Override
    public boolean delete(String name) {
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        int mark = rPMPkgMapper.delete(wrapper);
        return mark == 1;
    }

    @Override
    public boolean existRPM(String name) {
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        return rPMPkgMapper.exists(wrapper);
    }

    @Override
    public List<RPMPackage> queryByName(RPMPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        String name = condition.getName();

        Page<RPMPackageDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        if ("all".equals(name)) {
        } else {
            wrapper.eq("name", name);
        }
        
        Page<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
        List<RPMPackageDO> rPMDOs = resPage.getRecords();
        List<RPMPackage> res = RPMPackageConverter.toEntity(rPMDOs);
        
        return res;
    }

    @Override
    public boolean save(RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = RPMPackageConverter.toDataObjectForCreate(rPMPkg);
        int mark = rPMPkgMapper.insert(rPMPkgDO);
        return mark == 1;
    }

    @Override
    public boolean update(RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = RPMPackageConverter.toDataObjectForUpdate(rPMPkg);

        UpdateWrapper<RPMPackageDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", rPMPkg.getName());

        int mark = rPMPkgMapper.update(rPMPkgDO, wrapper);
        return mark == 1;
    }
    
}
