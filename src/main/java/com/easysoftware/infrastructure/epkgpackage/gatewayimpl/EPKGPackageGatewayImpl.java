package com.easysoftware.infrastructure.epkgpackage.gatewayimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter.EPKGPackageConverter;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.common.util.StringUtil;

@Component
public class EPKGPackageGatewayImpl implements EPKGPackageGateway{
    private static final Logger logger = LoggerFactory.getLogger(EPKGPackageGatewayImpl.class);
    @Autowired
    private EPKGPackageDOMapper ePKGPkgMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public int delete(List<String> ids) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.in("pkg_id", ids);
        int mark = ePKGPkgMapper.delete(wrapper);
        return mark;
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
        EPKGPackageMenuVo pkgVo = new EPKGPackageMenuVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);
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
    public int update(EPKGPackage epkg) {
        EPKGPackageDO epkgPackageDO = EPKGPackageConverter.toDataObjectForUpdate(epkg);

        UpdateWrapper<EPKGPackageDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("pkg_id", epkg.getPkgId());

        int mark = ePKGPkgMapper.update(epkgPackageDO, wrapper);
        return mark;
    }
    
    private Page<EPKGPackageDO> createPage(EPKGPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<EPKGPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }

    @Override
    public List<String> queryColumn(String column) {
        column = "category".equals(column) ? "category" : column;
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct " + column);
        List<EPKGPackageDO> rpmColumn = new ArrayList<>();
        try {
            rpmColumn = ePKGPkgMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("unsupported param: " + column);
        }
        column = StringUtil.underlineToCamel(column);
        List<String> res = EPKGPackageConverter.toColumn(rpmColumn, column);
        
        return res;
    }

    @Override
    public long queryTableLength() {
        return ePKGPkgMapper.selectCount(null);
    }

    @Override
    public EPKGPackageMenuVo selectOne(String name) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("pkg_id");
        wrapper.eq("name", name);
        wrapper.last("order by epkg_update_at desc limit 1");
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        if (epkgList.size() == 0) {
            return new EPKGPackageMenuVo();
        }
        EPKGPackageMenuVo res = EPKGPackageConverter.toMenu(epkgList.get(0));
        return res;
    }

    @Override
    public Collection<EPKGPackageDO> convertBatch(Collection<String> dataObject) {
        Collection<EPKGPackageDO> objList = new ArrayList<>();
        for (String obj : dataObject) {
            EPKGPackage epkg = ObjectMapperUtil.jsonToObject(obj, EPKGPackage.class);
            EPKGPackageDO epkgDO = EPKGPackageConverter.toDataObjectForCreate(epkg);
            logger.info("convert pkgId: {}", epkgDO.getPkgId());
            objList.add(epkgDO);
        }
        return objList;
    }

    @Override
    public List<EPKGPackageDetailVo> queryDetailByPkgId(String pkgId) {
        QueryWrapper<EPKGPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("pkg_id", pkgId);
        List<EPKGPackageDO> epkgList = ePKGPkgMapper.selectList(wrapper);
        List<EPKGPackageDetailVo> res = EPKGPackageConverter.toDetail(epkgList);
        return res;
    }
}
