package com.easysoftware.infrastructure.applicationversion.gatewayimpl.converter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.domain.common.utils.UuidUtil;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;

public class ApplicationVersionConvertor {
    public static ApplicationVersion toEntity(ApplicationVersionDO appVersionDO) {
        ApplicationVersion appVersion = new ApplicationVersion();
        BeanUtils.copyProperties(appVersionDO, appVersion);
        return appVersion;
    }

    public static List<ApplicationVersion> toEntity(List<ApplicationVersionDO> appDOs) {
        List<ApplicationVersion> res = new ArrayList<>();
        for (ApplicationVersionDO appDO : appDOs) {
            ApplicationVersion app = toEntity(appDO);
            res.add(app);
        }
        return res;
    }

    public static ApplicationVersionDO toDataObject(ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = new ApplicationVersionDO();
        BeanUtils.copyProperties(appVersion, appVersionDO);
        return appVersionDO;
    }

    public static ApplicationVersionDO toDataObjectForCreate(ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = toDataObject(appVersion);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        appVersionDO.setCreatedAt(currentTime);
        appVersionDO.setUpdateAt(currentTime);
        appVersionDO.setId(id);

        return appVersionDO;
    }

    public static ApplicationVersionDO toDataObjectForUpdate(ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = toDataObject(appVersion);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        appVersionDO.setUpdateAt(currentTime);
       
        return appVersionDO;
    }
}

