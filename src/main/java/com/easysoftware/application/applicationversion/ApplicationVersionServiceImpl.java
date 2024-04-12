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
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.applicationversion.gateway.ApplicationVersionGateway;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import com.easysoftware.infrastructure.mapper.ApplicationVersionDOMapper;
import com.easysoftware.kafka.Producer;

import jakarta.annotation.Resource;

@Service("ApplicationVersionService")
public class ApplicationVersionServiceImpl extends ServiceImpl<ApplicationVersionDOMapper, ApplicationVersionDO> implements ApplicationVersionService {
    @Autowired
    Producer kafkaProducer;

    @Value("${producer.topic}")
    String topicAppVersion;

    @Value("${api.repoInfo}")
    String repoInfoApi;

    @Resource
    ApplicationVersionGateway AppVersionGateway;


    @Override
    public ResponseEntity<Object> insertAppVersion(InputApplicationVersion inputAppVersion) {
        // 数据库中是否已存在该包
        boolean found = AppVersionGateway.existApp(inputAppVersion.getName());
        if (found) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        ApplicationVersion AppVersion = new ApplicationVersion();
        BeanUtils.copyProperties(inputAppVersion, AppVersion);

        Map<String, Object> kafkaMsg = ObjectMapperUtil.jsonToMap(AppVersion);
        kafkaMsg.put("table", "ApplicationVersion");
        kafkaMsg.put("unique", inputAppVersion.getName());
        kafkaProducer.sendMess(topicAppVersion + "_version", UuidUtil.getUUID32(), ObjectMapperUtil.writeValueAsString(kafkaMsg));

        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchAppVersion(ApplicationVersionSearchCondition condition) {
        Map<String, Object> res = AppVersionGateway.queryByName(condition);
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
            boolean deleted = AppVersionGateway.delete(name);
            if (deleted) {
                deletedNames.add(name);
            }
        }

        String msg = String.format("请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s"
                , names.toString(), existedNames.toString(), deletedNames.toString());
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    @Override
    public void saveDataObjectBatch(ArrayList<String> dataObject) {
        saveOrUpdateBatch(AppVersionGateway.convertBatch(dataObject));
    }
}
