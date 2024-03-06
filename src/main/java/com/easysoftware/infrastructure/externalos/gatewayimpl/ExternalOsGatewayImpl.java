package com.easysoftware.infrastructure.externalos.gatewayimpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.domain.externalos.ExternalOsUnique;
import com.easysoftware.domain.externalos.gateway.ExternalOsGateway;
import com.easysoftware.infrastructure.externalos.gatewayimpl.converter.ExternalOsConverter;
import com.easysoftware.infrastructure.externalos.gatewayimpl.dataobject.ExternalOsDO;
import com.easysoftware.infrastructure.mapper.ExternalOsDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

@Component
public class ExternalOsGatewayImpl implements ExternalOsGateway {
    @Autowired
    private ExternalOsDOMapper externalOsDOMapper;


    @Override
    public Map<String, Object> queryPkgMap(ExternalOsSearchCondiiton condition) {
        Page<ExternalOsDO> page = createPage(condition);
        QueryWrapper<ExternalOsDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ExternalOsDO(), condition, "");
        IPage<ExternalOsDO> resPage = externalOsDOMapper.selectPage(page, wrapper);
        List<ExternalOsDO> resList = resPage.getRecords();
        List<ExternalOs> exs = ExternalOsConverter.toEntity(resList);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", total),
            Map.entry("list", exs)
        );
        return res;
    }
    

    private Page<ExternalOsDO> createPage(ExternalOsSearchCondiiton condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<ExternalOsDO> page = new Page<>(pageNum, pageSize);
        return page;
    }


    @Override
    public boolean delete(String id) {
        QueryWrapper<ExternalOsDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        int mark = externalOsDOMapper.delete(wrapper);
        return mark == 1;
    }


    @Override
    public boolean existExternalOs(ExternalOsUnique uni) {
        QueryWrapper<ExternalOsDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ExternalOsDO(), uni, "");
        return externalOsDOMapper.exists(wrapper);
    }


    @Override
    public boolean existExternalOs(String id) {
        QueryWrapper<ExternalOsDO> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return externalOsDOMapper.exists(wrapper);
    }


    @Override
    public boolean save(ExternalOs ex) {
        ExternalOsDO exDO = ExternalOsConverter.toDataObjectForCreate(ex);
        int mark = externalOsDOMapper.insert(exDO);
        return mark == 1;
    }


    @Override
    public boolean update(ExternalOs ex) {
        ExternalOsDO exDO = ExternalOsConverter.toDataObjectForUpdate(ex);
        UpdateWrapper<ExternalOsDO> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", ex.getId());
        
        int mark = externalOsDOMapper.update(exDO, wrapper);
        return mark == 1;
    }
}
