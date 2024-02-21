package com.easysoftware.application.rpmpackage;

import java.util.List;

import com.easysoftware.application.rpmpackage.dto.InputRPMPackage;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;

public interface RPMPackageService {
    String insertRPMPkg(InputRPMPackage inputrPMPackage);
    String updateRPMPkg(InputRPMPackage inputrPMPackage);
    String deleteRPMPkg(List<String> names);
    String searchRPMPkg(RPMPackageSearchCondition condition);
}
