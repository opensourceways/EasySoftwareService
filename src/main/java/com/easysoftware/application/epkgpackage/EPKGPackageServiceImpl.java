package com.easysoftware.application.epkgpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ApiUtil;
import com.easysoftware.common.utils.Base64Util;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;

import jakarta.annotation.Resource;

@Service
public class EPKGPackageServiceImpl implements EPKGPackageService {
    @Resource
    EPKGPackageGateway ePKGPackageGateway;

    @Value("${api.repoMaintainer}")
    String repoMaintainerApi;

    @Value("${api.repoInfo}")
    String repoInfoApi;
    
    @Override
    public ResponseEntity<Object> deleteEPKGPkg(List<String> ids) {
        List<String> existedNames = new ArrayList<>();
        for (String id : ids) {
            boolean found = ePKGPackageGateway.existEPKG(id);
            if (found) {
                existedNames.add(id);
            }
        }

        List<String> deletedNames = new ArrayList<>();
        for (String id : existedNames) {
            boolean deleted = ePKGPackageGateway.delete(id);
            if (deleted) {
                deletedNames.add(id);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , ids.toString(), existedNames.toString(), deletedNames.toString());
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    @Override
    public ResponseEntity<Object> insertEPKGPkg(InputEPKGPackage inputEPKGPackage) {
        inputEPKGPackage = Base64Util.decode(inputEPKGPackage);

        if (StringUtils.isNotBlank(inputEPKGPackage.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }
        // 数据库中是否已存在该包
        EPKGPackageUnique unique = new EPKGPackageUnique();
        BeanUtils.copyProperties(inputEPKGPackage, unique);
        boolean found = ePKGPackageGateway.existEPKG(unique);
        if (found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        

        EPKGPackage epkgPkg = new EPKGPackage();
        BeanUtils.copyProperties(inputEPKGPackage, epkgPkg);
        epkgPkg = addEPKGInfo(epkgPkg);

        boolean succeed = ePKGPackageGateway.save(epkgPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public Map<String, Object> queryAllEPKGPkgMenu(EPKGPackageSearchCondition condition) {
        Map<String, Object> ePKGMenu = ePKGPackageGateway.queryMenuByName(condition);
        return ePKGMenu;
    }

    @Override
    public ResponseEntity<Object> searchEPKGPkg(EPKGPackageSearchCondition condition) {
        Map<String, Object> res = ePKGPackageGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateEPKGPkg(InputEPKGPackage inputEPKGPackage) {
        inputEPKGPackage = Base64Util.decode(inputEPKGPackage);
        
        if (StringUtils.isBlank(inputEPKGPackage.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }
        // 数据库中是否已存在该包
        boolean found = ePKGPackageGateway.existEPKG(inputEPKGPackage.getId());
        if (!found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }
        EPKGPackage epkgPkg = new EPKGPackage();
        BeanUtils.copyProperties(inputEPKGPackage, epkgPkg);
        epkgPkg = addEPKGInfo(epkgPkg);

        boolean succeed = ePKGPackageGateway.update(epkgPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return ResultUtil.success(HttpStatus.OK);
    }

    public EPKGPackage addEPKGInfo(EPKGPackage epkgPkg) {
        Map<String, String> maintainer = ApiUtil.getApiResponse(String.format(repoMaintainerApi, epkgPkg.getName()));
        epkgPkg.setMaintainerGiteeId(maintainer.get("gitee_id"));
        epkgPkg.setMaintainerId(maintainer.get("gitee_id"));
        epkgPkg.setMaintianerEmail(maintainer.get("email"));

        Map<String, String> info = ApiUtil.getApiResponse(String.format(repoInfoApi, epkgPkg.getName(), "rpm_openeuler"));
        epkgPkg.setOs(info.get("os"));
        epkgPkg.setArch(info.get("arch"));
        epkgPkg.setBinDownloadUrl(info.get("binDownloadUrl"));
        epkgPkg.setSrcDownloadUrl(info.get("srcDownloadUrl"));
        epkgPkg.setSrcRepo(info.get("srcRepo"));
        epkgPkg.setEpkgSize(info.get("appSize"));
        return epkgPkg;
    }
}
