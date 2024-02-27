package com.easysoftware.application.applicationpackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {
    @Resource
    ApplicationPackageGateway appPkgGateway;

    @Override
    public ResponseEntity<Object> insertAppPkg(InputApplicationPackage inputAppPkg) {
        // 数据库中是否已存在该包
        boolean found = appPkgGateway.existApp(inputAppPkg.getName());
        if (found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);

        boolean succeed = appPkgGateway.save(appPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchAppPkg(ApplicationPackageSearchCondition condition) {
        List<ApplicationPackage> res = appPkgGateway.queryByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateAppPkg(InputApplicationPackage inputAppPkg) {
        // 数据库中是否已存在该包
        boolean found = appPkgGateway.existApp(inputAppPkg.getName());
        if (!found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);

        boolean succeed = appPkgGateway.update(appPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteAppPkg(List<String> names) {
        List<String> existedNames = new ArrayList<>();
        for (String name : names) {
            boolean found = appPkgGateway.existApp(name);
            if (found) {
                existedNames.add(name);
            }
        }

        List<String> deletedNames = new ArrayList<>(); 
        for (String name : existedNames) {
            boolean deleted = appPkgGateway.delete(name);
            if (deleted) {
                deletedNames.add(name);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , names.toString(), existedNames.toString(), deletedNames.toString());
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchAppPkgIcon(String name) {
        byte[] data = appPkgGateway.getAppPkgIcon(name);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResultUtil.success(HttpStatus.OK, headers, data);
    }
}
