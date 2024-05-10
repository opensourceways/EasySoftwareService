package com.easysoftware.application.applicationversion;

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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service("ApplicationVersionService")
public class ApplicationVersionServiceImpl extends ServiceImpl<ApplicationVersionDOMapper,
        ApplicationVersionDO> implements ApplicationVersionService {

    /**
     * Autowired Kafka producer for sending messages.
     */
    @Autowired
    private Producer kafkaProducer;

    /**
     * Topic name for the Kafka producer related to application versions.
     */
    @Value("${producer.topic}")
    private String topicAppVersion;

    /**
     * API endpoint for repository information.
     */
    @Value("${api.repoInfo}")
    private String repoInfoApi;

    /**
     * Resource for interacting with Application Version Gateway.
     */
    @Resource
    private ApplicationVersionGateway appVersionGateway;


    /**
     * Inserts a new application version based on the provided input.
     *
     * @param inputAppVersion The input application version to be inserted.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> insertAppVersion(final InputApplicationVersion inputAppVersion) {
        // 数据库中是否已存在该包
        if (appVersionGateway.existApp(inputAppVersion.getName())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        }
        ApplicationVersion appVersion = new ApplicationVersion();
        BeanUtils.copyProperties(inputAppVersion, appVersion);

        Map<String, Object> kafkaMsg = ObjectMapperUtil.jsonToMap(appVersion);
        kafkaMsg.put("table", "ApplicationVersion");
        kafkaMsg.put("unique", inputAppVersion.getName());
        kafkaProducer.sendMess(topicAppVersion + "_version",
                UuidUtil.getUUID32(), ObjectMapperUtil.writeValueAsString(kafkaMsg));

        return ResultUtil.success(HttpStatus.OK);
    }

    /**
     * Searches for application versions based on the specified search conditions.
     *
     * @param condition The search conditions to filter application versions.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchAppVersion(final ApplicationVersionSearchCondition condition) {
        Map<String, Object> res = appVersionGateway.queryByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Updates an existing application version using the provided input.
     *
     * @param inputAppVersion The updated application version information.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> updateAppVersion(final InputApplicationVersion inputAppVersion) {
        // 数据库中是否已存在该包
        if (!appVersionGateway.existApp(inputAppVersion.getName())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0009);
        }
        ApplicationVersion appVersion = new ApplicationVersion();
        BeanUtils.copyProperties(inputAppVersion, appVersion);

        boolean succeed = appVersionGateway.update(appVersion);
        return succeed ? ResultUtil.success(HttpStatus.OK)
                : ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0004);
    }

    /**
     * Deletes application versions based on the provided list of names.
     *
     * @param names List of names of application versions to be deleted.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> deleteAppVersion(final List<String> names) {
        List<String> existedNames = names.stream().filter(appVersionGateway::existApp).toList();
        List<String> deletedNames = existedNames.stream().filter(appVersionGateway::delete).toList();
        String msg = String.format(Locale.ROOT, "请求删除的数据: %s, 在数据库中的数据: %s, 成功删除的数据: %s",
                names, existedNames, deletedNames);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    /**
     * Saves a batch of data objects.
     *
     * @param dataObject An ArrayList containing the data objects to be saved.
     */
    @Override
    public void saveDataObjectBatch(final ArrayList<String> dataObject) {
        saveOrUpdateBatch(appVersionGateway.convertBatch(dataObject));
    }
}
