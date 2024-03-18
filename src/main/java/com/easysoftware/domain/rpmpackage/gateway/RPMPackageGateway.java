package com.easysoftware.domain.rpmpackage.gateway;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

public interface RPMPackageGateway {
    boolean existRPM(RPMPackageUnique unique);
    boolean existRPM(String id);
    boolean save(RPMPackage appPkg);
    boolean update(RPMPackage appPkg);
    boolean delete(String id);
    Map<String, Object> queryDetailByName(RPMPackageSearchCondition condition);
    Map<String, Object> queryMenuByName(RPMPackageSearchCondition condition);
    List<String> queryColumn(String column);
    long queryTableLength();
    Collection<RPMPackageDO> convertBatch(Collection<String> dataObject);
    Map<String, Object> queryPartRPMPkgMenu(RPMPackageSearchCondition condition);
}
