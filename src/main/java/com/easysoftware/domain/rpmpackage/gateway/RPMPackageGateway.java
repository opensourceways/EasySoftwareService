package com.easysoftware.domain.rpmpackage.gateway;

import java.util.List;

import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;
import com.easysoftware.domain.rpmpackage.RPMPackage;

public interface RPMPackageGateway {
    boolean existRPM(String name);
    boolean save(RPMPackage appPkg);
    boolean update(RPMPackage appPkg);
    boolean delete(String name);
    List<RPMPackage> queryByName(RPMPackageSearchCondition condition);
}
