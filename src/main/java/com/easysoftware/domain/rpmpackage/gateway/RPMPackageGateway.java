package com.easysoftware.domain.rpmpackage.gateway;

import java.util.List;

import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.domain.rpmpackage.RPMPackageUnique;

public interface RPMPackageGateway {
    boolean existRPM(RPMPackageUnique unique);
    boolean existRPM(String id);
    boolean save(RPMPackage appPkg);
    boolean update(RPMPackage appPkg);
    boolean delete(String id);
    List<RPMPackage> queryByName(RPMPackageSearchCondition condition);
}
