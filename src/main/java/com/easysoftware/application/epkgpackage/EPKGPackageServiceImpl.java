package com.easysoftware.application.epkgpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

@Service("EPKGPackageService")
public class EPKGPackageServiceImpl extends ServiceImpl<EPKGPackageDOMapper, EPKGPackageDO> implements EPKGPackageService {
    @Resource
    EPKGPackageGateway ePKGPackageGateway;

    @Autowired
    Producer kafkaProducer;

    @Value("${api.repoMaintainer}")
    String repoMaintainerApi;

    @Value("${api.repoSig}")
    String repoSigApi;

    @Value("${producer.topic}")
    String topicAppVersion;
    
    @Override
    public ResponseEntity<Object> deleteEPKGPkg(List<String> ids) {
        int mark = ePKGPackageGateway.delete(ids);
        String msg = String.format("the number of deleted : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    @Override
    public ResponseEntity<Object> insertEPKGPkg(InputEPKGPackage inputEPKGPackage) {
        EPKGPackage epkgPkg = new EPKGPackage();
        BeanUtils.copyProperties(inputEPKGPackage, epkgPkg);

        Map<String, Object> kafkaMsg = ObjectMapperUtil.jsonToMap(inputEPKGPackage);
        kafkaMsg.put("table", "EPKGPackage");
        kafkaProducer.sendMess(topicAppVersion + "_epkg", UuidUtil.getUUID32(), ObjectMapperUtil.writeValueAsString(kafkaMsg));

        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public Map<String, Object> queryAllEPKGPkgMenu(EPKGPackageSearchCondition condition) {
        Map<String, Object> ePKGMenu = ePKGPackageGateway.queryMenuByName(condition);
        return ePKGMenu;
    }

    @Override
    public ResponseEntity<Object> searchEPKGPkg(EPKGPackageSearchCondition condition) {
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

        if (epkgList.size() != 0) {
            Map<String, Object> res = Map.ofEntries(
                Map.entry("total", epkgList.size()),
                Map.entry("list", epkgList)
            );
            return ResultUtil.success(HttpStatus.OK, res);
        }

        Map<String, Object> res = ePKGPackageGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    @Override
    public ResponseEntity<Object> updateEPKGPkg(InputEPKGPackage inputEPKGPackage) {
        EPKGPackage epkgPkg = new EPKGPackage();
        BeanUtils.copyProperties(inputEPKGPackage, epkgPkg);
        int mark = ePKGPackageGateway.update(epkgPkg);
        String msg = String.format("the number of updated : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    @Override
    public void saveDataObjectBatch(ArrayList<String> dataObject) {
        saveBatch(ePKGPackageGateway.convertBatch(dataObject));
    }

    @Override
    public boolean existApp(String unique) {
        EPKGPackageUnique uniquePkg = ObjectMapperUtil.jsonToObject(unique, EPKGPackageUnique.class);
        return ePKGPackageGateway.existEPKG(uniquePkg);
    }

    @Override
    public void saveDataObject(String dataObject) {
    }

 
}
