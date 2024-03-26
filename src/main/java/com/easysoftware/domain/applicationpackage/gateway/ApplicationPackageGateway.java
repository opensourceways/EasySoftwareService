package com.easysoftware.domain.applicationpackage.gateway;

import java.util.List;
import java.util.Map;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;

public interface ApplicationPackageGateway {
    boolean existApp(String name);
    boolean save(ApplicationPackage appPkg);
    boolean update(ApplicationPackage appPkg);
    boolean delete(String name);
    Map<String, Object> queryMenuByName(ApplicationPackageSearchCondition condition);
    Map<String, Object> queryDetailByName(ApplicationPackageSearchCondition condition);
    long queryTableLength();
    ApplicationPackageMenuVo selectOne(String name);
}
