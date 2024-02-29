package com.easysoftware.infrastructure.applicationpackage.gatewayimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConvertor;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.mapper.ApplicationPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

@Component
public class ApplicationPackageGatewayImpl implements ApplicationPackageGateway {
    @Autowired
    private ApplicationPackageDOMapper appPkgMapper;

    @Value("${apppkg.icon.path}")
    private String apppkgIconPath;

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
    public Map<String, Object> queryMenuByName(ApplicationPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<ApplicationPackageDO> page = new Page<>(pageNum, pageSize);

        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();

        IPage<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        long total = resPage.getTotal();
        List<ApplicationPackageMenuVo> menus = ApplicationPackageConvertor.toMenu(appDOs);

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", menus);
        return res;
    }

    @Override
    public Map<String, Object> queryDetailByName(ApplicationPackageSearchCondition condition) {
        Page<ApplicationPackageDO> page = createPage(condition);
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        String name = condition.getName();
        wrapper.eq("name", condition.getName());

        IPage<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        List<ApplicationPackageDetailVo> appDetails = ApplicationPackageConvertor.toDetail(appDOs);
        long total = resPage.getTotal();

        Map<String, Object> res = Map.ofEntries(
            Map.entry("total", total),
            Map.entry("list", appDetails)
        );
        return res;
    }

    private Page<ApplicationPackageDO> createPage(ApplicationPackageSearchCondition condition) {
        int pageNum = condition.getPageNum();
        int pageSize = condition.getPageSize();
        Page<ApplicationPackageDO> page = new Page<>(pageNum, pageSize);
        return page;
    }
}
