package com.easysoftware.application.rpmpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ApiUtil;
import com.easysoftware.common.utils.Base64Util;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

import jakarta.annotation.Resource;

@Service
public class RPMPackageServiceImpl implements RPMPackageService {
    @Resource
    RPMPackageGateway rPMPkgGateway;

    @Value("${api.repoMaintainer}")
    String repoMaintainerApi;

    @Value("${api.repoInfo}")
    String repoInfoApi;

    @Override
    public Map<String, Object> queryAllRPMPkgMenu(RPMPackageSearchCondition condition) {
        Map<String, Object> rPMMenu = rPMPkgGateway.queryMenuByName(condition);
        return rPMMenu;
    }

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
        inputrPMPackage = Base64Util.decode(inputrPMPackage);

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
        rPMPkg = addRPMPkgInfo(rPMPkg);

        boolean succeed = rPMPkgGateway.save(rPMPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchRPMPkg(RPMPackageSearchCondition condition) {
        Map<String, Object> res = rPMPkgGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateRPMPkg(InputRPMPackage inputrPMPackage) {
        inputrPMPackage = Base64Util.decode(inputrPMPackage);

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
        rPMPkg = addRPMPkgInfo(rPMPkg);

        boolean succeed = rPMPkgGateway.update(rPMPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    public RPMPackage addRPMPkgInfo(RPMPackage rPMPkg) {
        Map<String, String> maintainer = ApiUtil.getApiResponse(String.format(repoMaintainerApi, rPMPkg.getName()));
        rPMPkg.setMaintainerGiteeId(maintainer.get("gitee_id"));
        rPMPkg.setMaintianerEmail(maintainer.get("email"));

        Map<String, String> info = ApiUtil.getApiResponse(String.format(repoInfoApi, rPMPkg.getName(), "rpm_openeuler"));
        rPMPkg.setOs(info.get("os"));
        rPMPkg.setArch(info.get("arch"));
        rPMPkg.setBinDownloadUrl(info.get("binDownloadUrl"));
        rPMPkg.setSrcDownloadUrl(info.get("srcDownloadUrl"));
        rPMPkg.setSrcRepo(info.get("srcRepo"));
        rPMPkg.setRpmSize(info.get("appSize"));
        return rPMPkg;
    }
}
