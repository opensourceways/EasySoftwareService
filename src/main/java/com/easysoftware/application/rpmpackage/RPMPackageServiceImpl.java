package com.easysoftware.application.rpmpackage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.common.utils.HttpResult;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

import jakarta.annotation.Resource;

@Service
public class RPMPackageServiceImpl implements RPMPackageService {
    @Resource
    RPMPackageGateway rPMPkgGateway;

    @Override
    public String deleteRPMPkg(List<String> names) {
        List<String> existedNames = new ArrayList<>();
        for (String name : names) {
            boolean found = rPMPkgGateway.existRPM(name);
            if (found) {
                existedNames.add(name);
            }
        }

        List<String> deletedNames = new ArrayList<>();
        for (String name : existedNames) {
            boolean deleted = rPMPkgGateway.delete(name);
            if (deleted) {
                deletedNames.add(name);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , names.toString(), existedNames.toString(), deletedNames.toString());
        return HttpResult.ok(msg, null);
    }

    @Override
    public String insertRPMPkg(InputRPMPackage inputrPMPackage) {
        // 数据库中是否已存在该包
        boolean found = rPMPkgGateway.existRPM(inputrPMPackage.getName());
        if (found) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求的包已存在", null);
        }
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);

        boolean succeed = rPMPkgGateway.save(rPMPkg);
        if (!succeed) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "新增数据失败", null);
        }
        return HttpResult.ok("新增1条数据", null);
    }

    @Override
    public String searchRPMPkg(RPMPackageSearchCondition condition) {
        List<RPMPackage> res = rPMPkgGateway.queryByName(condition);
        return HttpResult.ok("完成查询数据", res);
    }

    @Override
    public String updateRPMPkg(InputRPMPackage inputrPMPackage) {
        // 数据库中是否已存在该包
        boolean found = rPMPkgGateway.existRPM(inputrPMPackage.getName());
        if (!found) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "请求的包不存在", null);
        }
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);

        boolean succeed = rPMPkgGateway.update(rPMPkg);
        if (!succeed) {
            return HttpResult.fail(HttpStatus.BAD_REQUEST.value(), "更新数据失败", null);
        }
        return HttpResult.ok("更新1条数据", null);
    }
    
}
