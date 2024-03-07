package com.easysoftware.infrastructure.epkgpackage.gatewayimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.utils.QueryWrapperUtil;
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
import com.power.common.util.StringUtil;

@Component
public class EPKGPackageGatewayImpl implements EPKGPackageGateway{
    @Autowired
    private EPKGPackageDOMapper ePKGPkgMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean delete(String id) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        int mark = ePKGPkgMapper.delete(wrapper);
        return mark == 1;
    }

    @Override
    public boolean existEPKG(EPKGPackageUnique unique) {
        Map<String, Object> map = objectMapper.convertValue(unique, HashMap.class);

        Map<String, Object> underlineMap = new HashMap<>();
        for (String key : map.keySet()) {
            String underlineKey = StringUtil.camelToUnderline(key);
            underlineMap.put(underlineKey, map.get(key));
        }
        
        QueryWrapper<EPKGPackageDO> wrapper = Wrappers.query();
        wrapper.setEntityClass(EPKGPackageDO.class);
        wrapper.allEq(underlineMap, false);
        return ePKGPkgMapper.exists(wrapper);
    }

    @Override
    public boolean existEPKG(String id) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return ePKGPkgMapper.exists(wrapper);
    }

    @Override
    public Map<String, Object> queryDetailByName(EPKGPackageSearchCondition condition) {
        Page<EPKGPackageDO> page = createPage(condition);
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(), 
                condition, "epkg_update_at");
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
        QueryWrapper<EPKGPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new EPKGPackageDO(), 
                condition, "epkg_update_at");
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
    public boolean save(EPKGPackage epkg) {
        EPKGPackageDO epkgPackageDO = EPKGPackageConverter.toDataObjectForCreate(epkg);
        int mark = ePKGPkgMapper.insert(epkgPackageDO);
        return mark == 1;
    }

    @Override
    public boolean update(EPKGPackage epkg) {
        EPKGPackageDO epkgPackageDO = EPKGPackageConverter.toDataObjectForUpdate(epkg);

        UpdateWrapper<EPKGPackageDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", epkg.getName());

        int mark = ePKGPkgMapper.update(epkgPackageDO, wrapper);
        return mark == 1;
    }
    
    private Page<EPKGPackageDO> createPage(EPKGPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<EPKGPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    @Override
    public List<String> queryColumn(String column) {
        column = "category".equals(column) ? "epkg_category" : column;
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct " + column);
        List<EPKGPackageDO> rpmColumn = ePKGPkgMapper.selectList(wrapper);

        column = StringUtil.underlineToCamel(column);
        List<String> res = EPKGPackageConverter.toColumn(rpmColumn, column);
        
        return res;
    }
}
