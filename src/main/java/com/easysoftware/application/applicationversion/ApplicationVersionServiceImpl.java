package com.easysoftware.application.applicationversion;
import java.util.ArrayList;
import java.util.List;

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
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import com.easysoftware.infrastructure.mapper.ApplicationVersionDOMapper;
import com.easysoftware.kafka.Producer;
// import com.easysoftware.domain.compatible.gateway.CompatibleGateway;

import jakarta.annotation.Resource;

@Service
public class ApplicationVersionServiceImpl extends ServiceImpl<ApplicationVersionDOMapper, ApplicationVersionDO> implements ApplicationVersionService {
    @Autowired
    Producer kafkaProducer;

    @Value("${producer.topic}")
    String topicAppVersion;

    @Resource
    ApplicationVersionGateway AppVersionGateway;

    // @Resource
    // CompatibleGateway compatibleGateway;

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

        kafkaProducer.sendMess(topicAppVersion, UuidUtil.getUUID32(), ObjectMapperUtil.writeValueAsString(AppVersion));

        // boolean succeed = AppVersionGateway.save(AppVersion);
        // if (!succeed) {
        //     return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        // }

        // 同步更新兼容版本状态
        // InputCompatible inputCompatible = new InputCompatible();
        // inputCompatible.setName(inputAppVersion.getName());
        // inputCompatible.setUpstreamVersion(inputAppVersion.getVersion());
        // inputCompatible.setCompatibleVersion("compatibleVersion");
        // String status = "ok" ;
        // inputCompatible.setStatus(status);
        // Compatible Compatible = new Compatible();
        // BeanUtils.copyProperties(inputCompatible, Compatible);
        // boolean succee = compatibleGateway.save(Compatible);

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
    
}
