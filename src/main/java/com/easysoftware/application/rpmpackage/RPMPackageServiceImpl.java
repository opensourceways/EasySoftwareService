package com.easysoftware.application.rpmpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.constant.MapConstant;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ApiUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.domain.rpmpackage.gateway.RPMPackageGateway;
import com.easysoftware.infrastructure.mapper.RPMPackageDOMapper;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.easysoftware.kafka.Producer;

import jakarta.annotation.Resource;

@Primary
@Service("RPMPackageService")
public class RPMPackageServiceImpl extends ServiceImpl<RPMPackageDOMapper, RPMPackageDO> implements RPMPackageService {
    @Autowired
    Producer kafkaProducer;

    @Resource
    RPMPackageGateway rPMPkgGateway;

    @Value("${api.repoMaintainer}")
    String repoMaintainerApi;

    @Value("${api.repoSig}")
    String repoSigApi;

    @Value("${api.repoDownload}")
    String repoDownloadApi;

    @Value("${producer.topic}")
    String topicAppVersion;

    @Override
    public Map<String, Object> queryAllRPMPkgMenu(RPMPackageSearchCondition condition) {
        Map<String, Object> rPMMenu = rPMPkgGateway.queryMenuByName(condition);
        return rPMMenu;
    }

    @Override
    public ResponseEntity<Object> deleteRPMPkg(List<String> ids) {
        int mark = rPMPkgGateway.delete(ids);
        String msg = String.format("the number of deleted : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    @Override
    public ResponseEntity<Object> insertRPMPkg(InputRPMPackage inputrPMPackage) {

        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);
        Map<String, Object> kafkaMsg = ObjectMapperUtil.jsonToMap(inputrPMPackage);
        kafkaMsg.put("table", "RPMPackage");
        kafkaProducer.sendMess(topicAppVersion + "_rpm", UuidUtil.getUUID32(), ObjectMapperUtil.writeValueAsString(kafkaMsg));
        return ResultUtil.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchRPMPkg(RPMPackageSearchCondition condition) {
        String pkgId = assemblePkgId(condition);

        List<RPMPackageDetailVo> rpmList = rPMPkgGateway.queryDetailByPkgId(pkgId);
        if (rpmList.size() != 0) {
            Map<String, Object> res = Map.ofEntries(
                Map.entry("total", rpmList.size()),
                Map.entry("list", rpmList)
            );
            return ResultUtil.success(HttpStatus.OK, res);
        }

        Map<String, Object> res = rPMPkgGateway.queryDetailByName(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    private String assemblePkgId(RPMPackageSearchCondition condition) {
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
        return pkgId;
    }

    @Override
    public ResponseEntity<Object> updateRPMPkg(InputRPMPackage inputrPMPackage) {
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(inputrPMPackage, rPMPkg);
        int mark = rPMPkgGateway.update(rPMPkg);
        String msg = String.format("the number of updated : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    @Override
    public boolean existApp(String unique){
        RPMPackageUnique uniquePkg = ObjectMapperUtil.jsonToObject(unique, RPMPackageUnique.class);
        return rPMPkgGateway.existRPM(uniquePkg);
    }

    @Override
    public void saveDataObject(String dataObject) {
        RPMPackage appVer = ObjectMapperUtil.jsonToObject(dataObject, RPMPackage.class);
        rPMPkgGateway.save(appVer);
    }

    @Override
    @Transactional
    public void saveDataObjectBatch(ArrayList<String> dataObject) {
        saveBatch(rPMPkgGateway.convertBatch(dataObject));
    }

    public RPMPackage addRPMPkgMaintainerInfo(RPMPackage rPMPkg) {
        Map<String, String> maintainer = ApiUtil.getApiResponseMaintainer(String.format(repoMaintainerApi, rPMPkg.getName()));
        rPMPkg.setMaintainerGiteeId(maintainer.get("gitee_id"));
        rPMPkg.setMaintainerId(maintainer.get("id"));
        rPMPkg.setMaintainerEmail(maintainer.get("email"));
        return rPMPkg;
    }

    public RPMPackage addRPMPkgRepoSig(RPMPackage rPMPkg) {
        String resp = ApiUtil.getApiResponseData(String.format(repoSigApi, rPMPkg.getName()));
        if (resp != null && MapConstant.CATEGORY_MAP.containsKey(resp)) {
            rPMPkg.setCategory(MapConstant.CATEGORY_MAP.get(resp));
        } else {
            rPMPkg.setCategory(MapConstant.CATEGORY_MAP.get("Other"));
        }
        return rPMPkg;
    }

    public RPMPackage addRPMPkgRepoDownload(RPMPackage rPMPkg) {
        String resp = ApiUtil.getApiResponseData(String.format(repoDownloadApi, rPMPkg.getName()));
        rPMPkg.setDownloadCount(resp);
        return rPMPkg;
    }
	
	@Override
    public List<RPMPackageDomainVo> queryPartAppPkgMenu(RPMPackageSearchCondition condition) {
        Map<String, Object> rPMMenu = rPMPkgGateway.queryPartRPMPkgMenu(condition);
        List<RPMPackageDomainVo> menus = (List<RPMPackageDomainVo>) rPMMenu.get("list");
        return menus;
	}

    
}
