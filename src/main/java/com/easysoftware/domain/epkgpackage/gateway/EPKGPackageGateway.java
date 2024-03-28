package com.easysoftware.domain.epkgpackage.gateway;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;

public interface EPKGPackageGateway {
    boolean existEPKG(EPKGPackageUnique unique);
    boolean existEPKG(String id);
    boolean save(EPKGPackage appPkg);
    boolean update(EPKGPackage appPkg);
    boolean delete(String id);
    Map<String, Object> queryDetailByName(EPKGPackageSearchCondition condition);
    List<EPKGPackageDetailVo> queryDetailByPkgId(String pkgId);
    Map<String, Object> queryMenuByName(EPKGPackageSearchCondition condition);
    List<String> queryColumn(String column);
    long queryTableLength();
    EPKGPackageMenuVo selectOne(String name);
    Collection<EPKGPackageDO> convertBatch(Collection<String> dataObject);
}
