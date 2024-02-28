package com.easysoftware.infrastructure.rpmpackage.gatewayimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConvertor;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.power.common.util.StringUtil;

@Component
public class RPMPackageGatewayImpl implements RPMPackageGateway {
    @Autowired
    private RPMPackageDOMapper rPMPkgMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public boolean delete(String id) {
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        int mark = rPMPkgMapper.delete(wrapper);
        return mark == 1;
    }

    @Override
    public boolean existRPM(RPMPackageUnique unique) {
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

    @Override
    public List<RPMPackageDetailVo> queryDetailByName(RPMPackageSearchCondition condition) {
        Page<RPMPackageDO> page = createPage(condition);

        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        String name = condition.getName();
        wrapper.eq("name", name);
             
        Page<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
        List<RPMPackageDO> rPMDOs = resPage.getRecords();
        List<RPMPackageDetailVo> res = RPMPackageConverter.toDetail(rPMDOs);
        
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

    @Override
    public boolean existRPM(String id) {
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return rPMPkgMapper.exists(wrapper);
    }

    @Override
    public List<RPMPackageMenuVo> queryMenuByName(RPMPackageSearchCondition condition) {
        Page<RPMPackageDO> page = createPage(condition);

        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();

        Page<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
        List<RPMPackageDO> rpmDOs = resPage.getRecords();
        List<RPMPackageMenuVo> res = RPMPackageConverter.toMenu(rpmDOs);

        return res;
    }

    private Page<RPMPackageDO> createPage(RPMPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<RPMPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }
    
}
