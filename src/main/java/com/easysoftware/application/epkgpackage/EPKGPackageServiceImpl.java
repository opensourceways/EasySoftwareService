package com.easysoftware.application.epkgpackage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.dto.InputEPKGPackage;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;
import com.easysoftware.kafka.Producer;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("EPKGPackageService")
public class EPKGPackageServiceImpl extends
        ServiceImpl<EPKGPackageDOMapper, EPKGPackageDO> implements EPKGPackageService {
    /**
     * Gateway for EPKG package operations.
     */
    @Resource
    private EPKGPackageGateway ePKGPackageGateway;

    /**
     * Kafka producer for messaging.
     */
    @Autowired
    private Producer kafkaProducer;

    /**
     * API endpoint for repository maintainers.
     */
    @Value("${api.repoMaintainer}")
    private String repoMaintainerApi;

    /**
     * API endpoint for repository signatures.
     */
    @Value("${api.repoSig}")
    private String repoSigApi;

    /**
     * Kafka topic for application version messages.
     */
    @Value("${producer.topic}")
    private String topicAppVersion;


    /**
     * Deletes EPKG packages by their names.
     *
     * @param ids List of package names to delete.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> deleteEPKGPkg(final List<String> ids) {
        int mark = ePKGPackageGateway.delete(ids);
        String msg = String.format("the number of deleted : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    /**
     * Inserts an EPKG package.
     *
     * @param inputEPKGPackage InputEPKGPackage object.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> insertEPKGPkg(final InputEPKGPackage inputEPKGPackage) {
        EPKGPackage epkgPkg = new EPKGPackage();
        BeanUtils.copyProperties(inputEPKGPackage, epkgPkg);

        Map<String, Object> kafkaMsg = ObjectMapperUtil.jsonToMap(inputEPKGPackage);
        kafkaMsg.put("table", "EPKGPackage");
        kafkaProducer.sendMess(topicAppVersion + "_epkg", UuidUtil.getUUID32(),
                ObjectMapperUtil.writeValueAsString(kafkaMsg));

        return ResultUtil.success(HttpStatus.OK);
    }

    /**
     * Queries all EPKG package menus based on search conditions.
     *
     * @param condition EPKGPackageSearchCondition object.
     * @return Map containing the menu data.
     */
    @Override
    public Map<String, Object> queryAllEPKGPkgMenu(final EPKGPackageSearchCondition condition) {
        return ePKGPackageGateway.queryMenuByName(condition);
    }

    /**
     * Searches for EPKG packages based on search conditions.
     *
     * @param condition EPKGPackageSearchCondition object.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchEPKGPkg(final EPKGPackageSearchCondition condition) {
        String os = StringUtils.trimToEmpty(condition.getOs());
        String subPath = StringUtils.trimToEmpty(condition.getSubPath());
        String name = StringUtils.trimToEmpty(condition.getName());
        String version = StringUtils.trimToEmpty(condition.getVersion());
        String arch = StringUtils.trimToEmpty(condition.getArch());

        StringBuilder cSb = new StringBuilder();
        cSb.append(os);
        cSb.append(subPath);
        cSb.append(name);
        cSb.append(version);
        cSb.append(arch);
        String pkgId = cSb.toString();

        List<EPKGPackageDetailVo> epkgList = ePKGPackageGateway.queryDetailByPkgId(pkgId);

        if (!epkgList.isEmpty()) {
            Map<String, Object> res = Map.ofEntries(
                    Map.entry("total", epkgList.size()),
                    Map.entry("list", epkgList)
            );
            return ResultUtil.success(HttpStatus.OK, res);
        }

        Map<String, Object> res = ePKGPackageGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Updates an EPKG package.
     *
     * @param inputEPKGPackage InputEPKGPackage object.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> updateEPKGPkg(final InputEPKGPackage inputEPKGPackage) {
        EPKGPackage epkgPkg = new EPKGPackage();
        BeanUtils.copyProperties(inputEPKGPackage, epkgPkg);
        int mark = ePKGPackageGateway.update(epkgPkg);
        String msg = String.format("the number of updated : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    /**
     * Saves a batch of data objects.
     *
     * @param dataObject ArrayList of data objects to save.
     */
    @Override
    public void saveDataObjectBatch(final ArrayList<String> dataObject) {
        saveOrUpdateBatch(ePKGPackageGateway.convertBatch(dataObject));
    }

    /**
     * Checks if an application with a given name exists.
     *
     * @param unique unique of the application.
     * @return boolean indicating if the application exists.
     */
    @Override
    public boolean existApp(final String unique) {
        EPKGPackageUnique uniquePkg = ObjectMapperUtil.jsonToObject(unique, EPKGPackageUnique.class);
        return ePKGPackageGateway.existEPKG(uniquePkg);
    }

    /**
     * Saves a single data object.
     *
     * @param dataObject Data object to save.
     */
    @Override
    public void saveDataObject(final String dataObject) {
    }


}
