package com.easysoftware.domain.applicationpackage.gateway;

import java.util.List;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;

public interface ApplicationPackageGateway {
    boolean existApp(String name);
    boolean save(ApplicationPackage appPkg);
    boolean update(ApplicationPackage appPkg);
    boolean delete(String name);
    List<ApplicationPackageMenuVo> queryMenuByName(ApplicationPackageSearchCondition condition);
    List<ApplicationPackageDetailVo> queryDetailByName(ApplicationPackageSearchCondition condition);
}
