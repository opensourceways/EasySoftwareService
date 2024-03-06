package com.easysoftware.domain.epkgpackage.gateway;

import java.util.List;
import java.util.Map;

import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.epkgpackage.EPKGPackageUnique;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;

public interface EPKGPackageGateway {
    boolean existEPKG(EPKGPackageUnique unique);
    boolean existEPKG(String id);
    boolean save(EPKGPackage appPkg);
    boolean update(EPKGPackage appPkg);
    boolean delete(String id);
    Map<String, Object> queryDetailByName(EPKGPackageSearchCondition condition);
    Map<String, Object> queryMenuByName(EPKGPackageSearchCondition condition);
    List<String> queryColumn(String column);
}
