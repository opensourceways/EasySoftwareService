package com.easysoftware.infrastructure.epkgpackage.gatewayimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter.EPKGPackageConverter;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EPKGPackageGatewayImpl implements EPKGPackageGateway{
    @Autowired
    private EPKGPackageDOMapper ePKGPkgMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean existRPM(EPKGPackageUnique unique) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean existRPM(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Map<String, Object> queryDetailByName(EPKGPackageSearchCondition condition) {
        Page<EPKGPackageDO> page = createPage(condition);
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        String name = condition.getName();
        wrapper.eq("name", name);
             
        IPage<EPKGPackageDO> resPage = ePKGPkgMapper.selectPage(page, wrapper);
        List<EPKGPackageDO> rPMDOs = resPage.getRecords();
        List<EPKGPackageDetailVo> rPMDetails = EPKGPackageConverter.toDetail(rPMDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", total),
            Map.entry("list", rPMDetails)
        );
        
        return res;
    }

    @Override
    public Map<String, Object> queryMenuByName(EPKGPackageSearchCondition condition) {
        Page<EPKGPackageDO> page = createPage(condition);
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();

        IPage<EPKGPackageDO> resPage = ePKGPkgMapper.selectPage(page, wrapper);
        List<EPKGPackageDO> rpmDOs = resPage.getRecords();
        List<EPKGPackageMenuVo> rPMMenus = EPKGPackageConverter.toMenu(rpmDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", total),
            Map.entry("list", rPMMenus)
        );
        return res;
    }

    @Override
    public boolean save(EPKGPackage appPkg) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean update(EPKGPackage appPkg) {
        // TODO Auto-generated method stub
        return false;
    }
    
    private Page<EPKGPackageDO> createPage(EPKGPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<EPKGPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }
}
