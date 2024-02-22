package com.easysoftware.application.rpmpackage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.result.MessageCode;
import com.easysoftware.result.Result;

import jakarta.annotation.Resource;

@Service
public class RPMPackageServiceImpl implements RPMPackageService {
    @Resource
    RPMPackageGateway rPMPkgGateway;

    @Override
    public ResponseEntity<Object> insertRPMPkg(InputRPMPackage inputrPMPackage) {
        // 数据库中是否已存在该包
        boolean found = rPMPkgGateway.existRPM(inputrPMPackage.getName());
        if (found) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);

        boolean succeed = rPMPkgGateway.save(rPMPkg);
        if (!succeed) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return Result.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchRPMPkg(RPMPackageSearchCondition condition) {
        List<RPMPackage> res = rPMPkgGateway.queryByName(condition);
        return Result.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateRPMPkg(InputRPMPackage inputrPMPackage) {
        // 数据库中是否已存在该包
        boolean found = rPMPkgGateway.existRPM(inputrPMPackage.getName());
        if (!found) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);

        boolean succeed = rPMPkgGateway.update(rPMPkg);
        if (!succeed) {
            return Result.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return Result.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> deleteRPMPkg(List<String> names) {
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
        return Result.success(HttpStatus.OK, msg);
    }
}
