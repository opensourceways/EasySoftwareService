package com.easysoftware.infrastructure.rpmpackage.gatewayimpl;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConverter;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.power.common.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
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
    public Map<String, Object> queryDetailByName(RPMPackageSearchCondition condition) {
        Page<RPMPackageDO> page = createPage(condition);
        QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(), 
                condition, "rpm_update_at");
        IPage<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
        List<RPMPackageDO> rPMDOs = resPage.getRecords();
        List<RPMPackageDetailVo> rPMDetails = RPMPackageConverter.toDetail(rPMDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", total),
            Map.entry("list", rPMDetails)
        );
        
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
    public Map<String, Object> queryMenuByName(RPMPackageSearchCondition condition) {
        Page<RPMPackageDO> page = createPage(condition);
        QueryWrapper<RPMPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new RPMPackageDO(), 
                condition, "rpm_update_at");
        IPage<RPMPackageDO> resPage = rPMPkgMapper.selectPage(page, wrapper);
        List<RPMPackageDO> rpmDOs = resPage.getRecords();
        List<RPMPackageMenuVo> rPMMenus = RPMPackageConverter.toMenu(rpmDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", total),
            Map.entry("list", rPMMenus)
        );
        return res;
    }

    private Page<RPMPackageDO> createPage(RPMPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<RPMPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    @Override
    public List<String> queryColumn(String column) {
        column = "category".equals(column) ? "rpm_category" : column;
        QueryWrapper<RPMPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct " + column);
        List<RPMPackageDO> rpmColumn = rPMPkgMapper.selectList(wrapper);

        column = StringUtil.underlineToCamel(column);
        List<String> res = RPMPackageConverter.toColumn(rpmColumn, column);

        return res;
    }

    @Override
    public long queryTableLength() {
        return rPMPkgMapper.selectCount(null);
    }

    @Override
    public Collection<RPMPackageDO> convertBatch(Collection<String> dataObject){
        Collection<RPMPackageDO> ObjList = new ArrayList<>();
        for (String obj : dataObject) {
            RPMPackage rpmPackage = ObjectMapperUtil.jsonToObject(obj, RPMPackage.class);
            RPMPackageDO rpmDO = RPMPackageConverter.toDataObjectForCreate(rpmPackage);
            ObjList.add(rpmDO);
        }
        return ObjList;
    }
}
