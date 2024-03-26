package com.easysoftware.application.applicationpackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.dto.InputApplicationPackage;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.common.constant.MapConstant;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.obs.ObsService;
import com.easysoftware.common.utils.ApiUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;

import jakarta.annotation.Resource;

@Service
public class ApplicationPackageServiceImpl implements ApplicationPackageService {
    @Resource
    ApplicationPackageGateway appPkgGateway;

    @Autowired
    ObsService obsService;

    @Value("${api.repoMaintainer}")
    String repoMaintainerApi;

    @Value("${api.repoInfo}")
    String repoInfoApi;

    @Value("${api.repoDownload}")
    String repoDownloadApi;

    @Value("${api.repoSig}")
    String repoSigApi;

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
        // appPkg = addAppkgRepoSig(appPkg);
        appPkg = addAppkgRepoDownload(appPkg);

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
        // appPkg = addAppkgRepoSig(appPkg);
        appPkg = addAppkgRepoDownload(appPkg);

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

    public ApplicationPackage addAppPkgInfo(ApplicationPackage appPkg) {
        Map<String, String> maintainer = ApiUtil.getApiResponseMaintainer(String.format(repoMaintainerApi, appPkg.getName()));
        appPkg.setMaintainerGiteeId(maintainer.get("gitee_id"));
        appPkg.setMaintainerId(maintainer.get("id"));
        appPkg.setMaintainerEmail(maintainer.get("email"));

        Map<String, String> info = ApiUtil.getApiResponseMap(String.format(repoInfoApi, appPkg.getName(), "app_openeuler"));
        appPkg.setOs(info.get("os"));
        if (StringUtils.isBlank(info.get("latest_version")) && StringUtils.isBlank(info.get("os_version"))) {
            appPkg.setAppVer("");
        } else {
            appPkg.setAppVer(info.get("latest_version") + "-" + info.get("os_version"));
        }
        appPkg.setArch(info.get("arch"));
        appPkg.setAppSize(info.get("appSize"));
        appPkg.setBinDownloadUrl(info.get("binDownloadUrl"));
        appPkg.setSrcDownloadUrl(info.get("srcDownloadUrl"));
        appPkg.setSrcRepo(info.get("srcRepo"));
        appPkg.setIconUrl(obsService.generateUrl(appPkg.getName()));
        return appPkg;
    }

    public ApplicationPackage addAppkgRepoDownload(ApplicationPackage appPkg) {
        String resp = ApiUtil.getApiResponseData(String.format(repoDownloadApi, appPkg.getName()));
        appPkg.setDownloadCount(resp);
        return appPkg;
    }

    public ApplicationPackage addAppkgRepoSig(ApplicationPackage appPkg) {
        String resp = ApiUtil.getApiResponseData(String.format(repoSigApi, appPkg.getName()));
        if (resp != null && MapConstant.CATEGORY_MAP.containsKey(resp)) {
            appPkg.setCategory(MapConstant.CATEGORY_MAP.get(resp));
        } else {
            appPkg.setCategory(MapConstant.CATEGORY_MAP.get("Other"));
        }
        return appPkg;
    }

    @Override
    public List<ApplicationPackageMenuVo> queryPkgMenuList(ApplicationPackageSearchCondition condition) {
        Map<String, Object> map = appPkgGateway.queryMenuByName(condition);
        List<ApplicationPackageMenuVo> appMenus = (List<ApplicationPackageMenuVo>) map.get("list");
        return appMenus;
    }

    
}
