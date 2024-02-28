package com.easysoftware.application.applicationpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.utils.HttpClientUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.annotation.Resource;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {
    @Resource
    ApplicationPackageGateway appPkgGateway;

    @Value("${api.repoMaintainer}")
    String repoMaintainerApi;

    @Override
    public ResponseEntity<Object> insertAppPkg(InputApplicationPackage inputAppPkg) {
        // 数据库中是否已存在该包
        boolean found = appPkgGateway.existApp(inputAppPkg.getName());
        if (found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);
        String response = HttpClientUtil.getHttpClient(String.format(repoMaintainerApi, appPkg.getName()));
        if (response != null) {
            JsonNode info = ObjectMapperUtil.toJsonNode(response);
            if (info.get("code").asInt() == 200 && !info.get("data").isNull()) {
                JsonNode infoData = info.get("data");
                appPkg.setMaintainerGiteeId(infoData.get("gitee_id").asText());
                appPkg.setMaintainerEmail(infoData.get("email").asText());
            }
        }
        boolean succeed = appPkgGateway.save(appPkg);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        }
        return ResultUtil.success(HttpStatus.OK);
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
    public ResponseEntity<Object> searchAppPkg(ApplicationPackageSearchCondition condition) {

        // 查找详情页
        List<ApplicationPackageDetailVo> res = appPkgGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public List<ApplicationPackageMenuVo> queryAllAppPkgMenu(ApplicationPackageSearchCondition condition) {
        List<ApplicationPackageMenuVo> menuList = appPkgGateway.queryMenuByName(condition);

        return menuList;
    }
}
