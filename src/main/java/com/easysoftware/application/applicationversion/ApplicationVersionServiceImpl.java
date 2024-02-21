package com.easysoftware.application.applicationversion;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.result.MessageCode;
import com.easysoftware.result.Result;

import jakarta.annotation.Resource;

@Service
public class ApplicationVersionServiceImpl implements ApplicationVersionService {
    @Resource
    ApplicationVersionGateway AppVersionGateway;

    @Override
    public ResponseEntity<Object> insertAppVersion(InputApplicationVersion inputAppVersion) {
        // 数据库中是否已存在该包
        boolean found = AppVersionGateway.existApp(inputAppVersion.getName());
        if (found) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        ApplicationVersion AppVersion = new ApplicationVersion();
        BeanUtils.copyProperties(inputAppVersion, AppVersion);

        boolean succeed = AppVersionGateway.save(AppVersion);
        if (!succeed) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return Result.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchAppVersion(ApplicationVersionSearchCondition condition) {
        List<ApplicationVersion> res = AppVersionGateway.queryByName(condition);
        return Result.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateAppVersion(InputApplicationVersion inputAppVersion) {
        // 数据库中是否已存在该包
        boolean found = AppVersionGateway.existApp(inputAppVersion.getName());
        if (!found) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }
        ApplicationVersion AppVersion = new ApplicationVersion();
        BeanUtils.copyProperties(inputAppVersion, AppVersion);

        boolean succeed = AppVersionGateway.update(AppVersion);
        if (!succeed) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return Result.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteAppVersion(List<String> names) {
        List<String> existedNames = new ArrayList<>();
        for (String name : names) {
            boolean found = AppVersionGateway.existApp(name);
            if (found) {
                existedNames.add(name);
            }
        }

        List<String> deletedNames = new ArrayList<>(); 
        for (String name : existedNames) {
            boolean deleted = AppVersionGateway.delete(names);
            if (deleted) {
                deletedNames.add(name);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , names.toString(), existedNames.toString(), deletedNames.toString());
        return Result.success(HttpStatus.OK, msg);
    }
    
}
