package com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.common.utils.UuidUtil;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;

public class ApplicationPackageConvertor {
    public static ApplicationPackage toEntity(ApplicationPackageDO appPkgDO) {
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(appPkgDO, appPkg);
        return appPkg;
    }

    public static List<ApplicationPackage> toEntity(List<ApplicationPackageDO> appDOs) {
        List<ApplicationPackage> res = new ArrayList<>();
        for (ApplicationPackageDO appDO : appDOs) {
            ApplicationPackage app = toEntity(appDO);
            res.add(app);
        }
        return res;
    }

    public static ApplicationPackageDO toDataObject(ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = new ApplicationPackageDO();
        BeanUtils.copyProperties(appPkg, appPkgDO);
        return appPkgDO;
    }

    public static ApplicationPackageDO toDataObjectForCreate(ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = toDataObject(appPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        appPkgDO.setCreateAt(currentTime);
        appPkgDO.setUpdateAt(currentTime);
        appPkgDO.setId(id);

        return appPkgDO;
    }

    public static ApplicationPackageDO toDataObjectForUpdate(ApplicationPackage appPkg) {
        ApplicationPackageDO appPkgDO = toDataObject(appPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        appPkgDO.setUpdateAt(currentTime);
       
        return appPkgDO;
    }
}

