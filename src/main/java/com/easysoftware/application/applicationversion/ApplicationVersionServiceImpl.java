package com.easysoftware.application.applicationversion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.application.applicationversion.dto.InputApplicationVersion;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ApiUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import com.easysoftware.infrastructure.mapper.ApplicationVersionDOMapper;
import com.easysoftware.kafka.Producer;

import jakarta.annotation.Resource;

@Service
public class ApplicationVersionServiceImpl extends ServiceImpl<ApplicationVersionDOMapper, ApplicationVersionDO> implements ApplicationVersionService {
    @Autowired
    Producer kafkaProducer;

    @Value("${producer.topic}")
    String topicAppVersion;

    @Value("${api.repoInfo}")
    String repoInfoApi;

    @Resource
    ApplicationVersionGateway AppVersionGateway;

    public String getCompatible(String name) {
        return null;
    }

    @Override
    public ResponseEntity<Object> insertAppVersion(InputApplicationVersion inputAppVersion) {
        // 数据库中是否已存在该包
        boolean found = AppVersionGateway.existApp(inputAppVersion.getName());
        if (found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        ApplicationVersion AppVersion = new ApplicationVersion();
        BeanUtils.copyProperties(inputAppVersion, AppVersion);
        AppVersion = addAppPkgInfo(AppVersion);

        kafkaProducer.sendMess(topicAppVersion, UuidUtil.getUUID32(), ObjectMapperUtil.writeValueAsString(AppVersion));

        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchAppVersion(ApplicationVersionSearchCondition condition) {
        List<ApplicationVersion> res = AppVersionGateway.queryByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateAppVersion(InputApplicationVersion inputAppVersion) {
        // 数据库中是否已存在该包
        boolean found = AppVersionGateway.existApp(inputAppVersion.getName());
        if (!found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }
        ApplicationVersion AppVersion = new ApplicationVersion();
        BeanUtils.copyProperties(inputAppVersion, AppVersion);
        AppVersion = addAppPkgInfo(AppVersion);

        boolean succeed = AppVersionGateway.update(AppVersion);
        if (!succeed) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
        }
        return ResultUtil.success(HttpStatus.OK);
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
        return ResultUtil.success(HttpStatus.OK, msg);
    }
    
    public ApplicationVersion addAppPkgInfo(ApplicationVersion appVer) {
        Map<String, String> info = ApiUtil.getApiResponse(String.format(repoInfoApi, appVer.getName(), "app_openeuler"));
        appVer.setCompatibleVersion(info.get("latest_version"));

        info = ApiUtil.getApiResponse(String.format(repoInfoApi, appVer.getName(), "app_up"));
        appVer.setUpstreamVersion(info.get("latest_version"));

        info = ApiUtil.getApiResponse(String.format(repoInfoApi, appVer.getName(), "app_openeuler_ci"));
        appVer.setCiVersion(info.get("latest_version"));

        if (appVer.getCompatibleVersion() == null) {
            appVer.setStatus("MISSING");
        } else if (appVer.getUpstreamVersion().equals(appVer.getCompatibleVersion())) {
            appVer.setStatus("OK");
        } else {
            appVer.setStatus("OUTDATED");
        }
        return appVer;
    }
}
