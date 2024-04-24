package com.easysoftware.infrastructure.applicationpackage.gatewayimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ClassField;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter.ApplicationPackageConverter;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.mapper.ApplicationPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.power.common.util.StringUtil;

import okhttp3.internal.ws.RealWebSocket.Message;

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
        ApplicationPackageDO appPkgDO = ApplicationPackageConverter.toDataObjectForCreate(appPkg);
        int mark = appPkgMapper.insert(appPkgDO);
        return mark == 1;
    }


    @Override
    public boolean update(ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = ApplicationPackageConverter.toDataObjectForUpdate(appPkg);

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
        ApplicationPackageMenuVo pkgVo = new ApplicationPackageMenuVo();
        List<String> columns = ClassField.getFieldNames(pkgVo);
        wrapper.select(columns);

        IPage<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        long total = resPage.getTotal();
        List<ApplicationPackageMenuVo> menus = ApplicationPackageConverter.toMenu(appDOs);

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", menus);
        return res;
    }

    @Override
    public Map<String, Object> queryDetailByName(ApplicationPackageSearchCondition condition) {
        Page<ApplicationPackageDO> page = createPage(condition);
        QueryWrapper<ApplicationPackageDO> wrapper = QueryWrapperUtil.createQueryWrapper(new ApplicationPackageDO()
                , condition, "");

        IPage<ApplicationPackageDO> resPage = appPkgMapper.selectPage(page, wrapper);
        List<ApplicationPackageDO> appDOs = resPage.getRecords();
        List<ApplicationPackageDetailVo> appDetails = ApplicationPackageConverter.toDetail(appDOs);
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

    @Override
    public long queryTableLength() {
        return appPkgMapper.selectCount(null);
    }


    @Override
    public ApplicationPackageMenuVo selectOne(String name) {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("pkg_id");
        wrapper.eq("name", name);
        wrapper.last("limit 1");
        List<ApplicationPackageDO> appList = appPkgMapper.selectList(wrapper);
        if (appList.size() == 0) {
            return new ApplicationPackageMenuVo();
        }
        ApplicationPackageMenuVo res =ApplicationPackageConverter.toMenu(appList.get(0));
        return res;
    }


    @Override
    public List<ApplicationPackageDetailVo> queryDetailByPkgId(String pkgId) {
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        wrapper.eq("pkg_id", pkgId);
        List<ApplicationPackageDO> appList = appPkgMapper.selectList(wrapper);
        List<ApplicationPackageDetailVo> res =  ApplicationPackageConverter.toDetail(appList);
        return res;
    }

    @Override
    public Map<String, List<String>> queryColumn(List<String> columns) {
        Map<String, List<String>> res = new HashMap<>();
        for (String column : columns) {
            List<String> colList = queryColumn(column);
            res.put(column, colList);
        }
        return res;
    }

    public List<String> queryColumn(String column) {
        column = "category".equals(column) ? "category" : column;
        QueryWrapper<ApplicationPackageDO> wrapper = new QueryWrapper<>();
        wrapper.select("distinct " + column);
        List<ApplicationPackageDO> rpmColumn = new ArrayList<>();
        try {
            rpmColumn = appPkgMapper.selectList(wrapper);
        } catch (BadSqlGrammarException e) {
            throw new ParamErrorException("unsupported param: " + column);
        }
    
        column = StringUtil.underlineToCamel(column);
        List<String> res = ApplicationPackageConverter.toColumn(rpmColumn, column);
    
        return res;
    }
}
