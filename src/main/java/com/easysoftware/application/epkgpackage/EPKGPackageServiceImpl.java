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
import com.easysoftware.common.constant.MapConstant;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ApiUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.epkgpackage.gateway.EPKGPackageGateway;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.mapper.EPKGPackageDOMapper;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.easysoftware.kafka.Producer;
import com.power.common.util.ObjectUtil;

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

        if (StringUtils.isNotBlank(inputEPKGPackage.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }
        // 数据库中是否已存在该包
        // EPKGPackageUnique unique = new EPKGPackageUnique();
        // BeanUtils.copyProperties(inputEPKGPackage, unique);
        // boolean found = ePKGPackageGateway.existEPKG(unique);
        // if (found) {
        //     return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0008);
        // }
        

        EPKGPackage epkgPkg = new EPKGPackage();
        BeanUtils.copyProperties(inputEPKGPackage, epkgPkg);
        // epkgPkg = addEPKGInfo(epkgPkg);

        Map<String, Object> kafkaMsg = ObjectMapperUtil.jsonToMap(inputEPKGPackage);
        kafkaMsg.put("table", "EPKGPackage");
        kafkaProducer.sendMess(topicAppVersion + "_epkg", UuidUtil.getUUID32(), ObjectMapperUtil.writeValueAsString(kafkaMsg));

        // boolean succeed = ePKGPackageGateway.save(epkgPkg);
        // if (!succeed) {
        //     return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
        // }
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
        Map<String, String> maintainer = ApiUtil.getApiResponseMaintainer(String.format(repoMaintainerApi, epkgPkg.getName()));
        epkgPkg.setMaintainerGiteeId(maintainer.get("gitee_id"));
        epkgPkg.setMaintainerId(maintainer.get("id"));
        epkgPkg.setMaintainerEmail(maintainer.get("email"));
        return epkgPkg;
    }

    public EPKGPackage addRPMPkgRepoSig(EPKGPackage epkgPkg) {
        String resp = ApiUtil.getApiResponseData(String.format(repoSigApi, epkgPkg.getName()));
        if (resp != null && MapConstant.CATEGORY_MAP.containsKey(resp)) {
            epkgPkg.setCategory(MapConstant.CATEGORY_MAP.get(resp));
        } else {
            epkgPkg.setCategory(MapConstant.CATEGORY_MAP.get("Other"));
        }
        return epkgPkg;
    }

    @Override
    public void saveDataObject(String dataObject) {
        EPKGPackage pkg = ObjectMapperUtil.jsonToObject(dataObject, EPKGPackage.class);
        ePKGPackageGateway.save(pkg);
        // TODO Auto-generated method stub
        
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
    public List<EPKGPackageDO> queryPartAppPkgMenu(EPKGPackageSearchCondition condition) {
        // TODO Auto-generated method stub
        return null;
    }
}
