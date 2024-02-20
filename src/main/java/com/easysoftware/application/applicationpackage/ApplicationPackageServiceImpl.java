package com.easysoftware.application.applicationpackage;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.common.utils.HttpResult;

import jakarta.annotation.Resource;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {
    @Resource
    ApplicationPackageGateway appPkgGateway;

    @Override
    public String insertAppPkg(InputApplicationPackage inputAppPkg) {
        // 数据库中是否已存在该包
        boolean found = appPkgGateway.existApp(inputAppPkg.getName());
        if (found) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求的包已存在", null);
        }
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);

        boolean succeed = appPkgGateway.save(appPkg);
        if (!succeed) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "新增数据失败", null);
        }
        return HttpResult.ok("新增1条数据", null);
    }

    @Override
    public String searchAppPkg(ApplicationPackageSearchCondition condition) {
        List<ApplicationPackage> res = appPkgGateway.queryByName(condition);
        return HttpResult.ok("完成查询数据", res);
    }

    @Override
    public String updateAppPkg(InputApplicationPackage inputAppPkg) {
        // 数据库中是否已存在该包
        boolean found = appPkgGateway.existApp(inputAppPkg.getName());
        if (!found) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求的包不存在", null);
        }
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);

        boolean succeed = appPkgGateway.update(appPkg);
        if (!succeed) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "更新数据失败", null);
        }
        return HttpResult.ok("更新1条数据", null);
    }

    @Override
    public String deleteAppPkg(List<String> names) {
        List<String> existedNames = new ArrayList<>();
        for (String name : names) {
            boolean found = appPkgGateway.existApp(name);
            if (found) {
                existedNames.add(name);
            }
        }

        List<String> deletedNames = new ArrayList<>(); 
        for (String name : existedNames) {
            boolean deleted = appPkgGateway.delete(names);
            if (deleted) {
                deletedNames.add(name);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , names.toString(), existedNames.toString(), deletedNames.toString());
        return HttpResult.ok(msg, null);
    }
    
}
