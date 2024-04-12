package com.easysoftware.domain.rpmpackage.gateway;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDomainVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

public interface RPMPackageGateway {
    boolean existRPM(RPMPackageUnique unique);
    boolean existRPM(String id);
    boolean save(RPMPackage appPkg);
    int update(RPMPackage appPkg);
    int delete(List<String> id);
    Map<String, Object> queryDetailByName(RPMPackageSearchCondition condition);
    List<RPMPackageDetailVo> queryDetailByPkgId(String pkgId);
    Map<String, Object> queryMenuByName(RPMPackageSearchCondition condition);
    List<String> queryColumn(String column);
    long queryTableLength();
    Collection<RPMPackageDO> convertBatch(Collection<String> dataObject);
    Map<String, Object> queryPartRPMPkgMenu(RPMPackageSearchCondition condition);
    RPMPackageMenuVo selectOne(String name);
}
