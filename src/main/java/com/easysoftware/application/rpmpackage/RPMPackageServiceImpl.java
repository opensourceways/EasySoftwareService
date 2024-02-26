package com.easysoftware.application.rpmpackage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

import jakarta.annotation.Resource;

@Service
public class RPMPackageServiceImpl implements RPMPackageService {
    @Resource
    RPMPackageGateway rPMPkgGateway;

    @Override
    public ResponseEntity<Object> deleteRPMPkg(List<String> ids) {
        List<String> existedNames = new ArrayList<>();
        for (String id : ids) {
            boolean found = rPMPkgGateway.existRPM(id);
            if (found) {
                existedNames.add(id);
            }
        }

        List<String> deletedNames = new ArrayList<>();
        for (String id : existedNames) {
            boolean deleted = rPMPkgGateway.delete(id);
            if (deleted) {
                deletedNames.add(id);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , ids.toString(), existedNames.toString(), deletedNames.toString());
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> insertRPMPkg(InputRPMPackage inputrPMPackage) {
        if (StringUtils.isNotBlank(inputrPMPackage.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }
        // 数据库中是否已存在该包
        RPMPackageUnique unique = new RPMPackageUnique();
        BeanUtils.copyProperties(inputrPMPackage, unique);
        boolean found = rPMPkgGateway.existRPM(unique);
        if (found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);

        boolean succeed = rPMPkgGateway.save(rPMPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchRPMPkg(RPMPackageSearchCondition condition) {
        List<RPMPackage> res = rPMPkgGateway.queryByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateRPMPkg(InputRPMPackage inputrPMPackage) {
        if (StringUtils.isBlank(inputrPMPackage.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }
        // 数据库中是否已存在该包
        boolean found = rPMPkgGateway.existRPM(inputrPMPackage.getId());
        if (!found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);

        boolean succeed = rPMPkgGateway.update(rPMPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return ResultUtil.success(HttpStatus.OK);
    }
    
}
