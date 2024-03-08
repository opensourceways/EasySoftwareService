package com.easysoftware.application.applicationpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ApiUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;

import jakarta.annotation.Resource;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {
    @Resource
    ApplicationPackageGateway appPkgGateway;

    @Value("${api.repoMaintainer}")
    String repoMaintainerApi;

    @Value("${api.repoInfo}")
    String repoInfoApi;

    @Value("${obs.endpoint}")
    String obsEndpoint;

    @Value("${obs.bucket}")
    String obsBucketName;

    @Override
    public ResponseEntity<Object> insertAppPkg(InputApplicationPackage inputAppPkg) {
        // 数据库中是否已存在该包
        boolean found = appPkgGateway.existApp(inputAppPkg.getName());
        if (found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(inputAppPkg, appPkg);
        appPkg = addAppPkgInfo(appPkg);

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

        appPkg = addAppPkgInfo(appPkg);

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
        Map<String, Object> res = appPkgGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public Map<String, Object> queryAllAppPkgMenu(ApplicationPackageSearchCondition condition) {
        Map<String, Object> res = appPkgGateway.queryMenuByName(condition);

        return res;
    }

    public ApplicationPackage addAppPkgInfo(ApplicationPackage appPkg) {
        Map<String, String> maintainer = ApiUtil.getApiResponse(String.format(repoMaintainerApi, appPkg.getName()));
        appPkg.setMaintainerGiteeId(maintainer.get("gitee_id"));
        appPkg.setMaintainerId(maintainer.get("gitee_id"));
        appPkg.setMaintainerEmail(maintainer.get("email"));

        Map<String, String> info = ApiUtil.getApiResponse(String.format(repoInfoApi, appPkg.getName(), "app_openeuler"));
        appPkg.setOs(info.get("os"));
        appPkg.setAppVer(info.get("latest_version") + "-" + info.get("os_version"));
        appPkg.setArch(info.get("arch"));
        appPkg.setAppSize(info.get("appSize"));
        appPkg.setBinDownloadUrl(info.get("binDownloadUrl"));
        appPkg.setSrcDownloadUrl(info.get("srcDownloadUrl"));
        appPkg.setSrcRepo(info.get("srcRepo"));
        appPkg.setIconUrl(generateUrl(appPkg.getName() + ".png"));
        return appPkg;
    }

    private String generateUrl(String objectKey) {
        String publicUrl = "https://" + obsBucketName + "." + obsEndpoint + "/" + objectKey;
        return publicUrl;
    }
}
