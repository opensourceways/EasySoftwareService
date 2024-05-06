package com.easysoftware.infrastructure.applicationversion.gatewayimpl.converter;

import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class ApplicationVersionConvertor {

    // Private constructor to prevent instantiation of the utility class
    private ApplicationVersionConvertor() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate ApplicationVersionConvertor class");
    }

    /**
     * Convert an ApplicationVersionDO object to an ApplicationVersion entity.
     *
     * @param appVersionDO The ApplicationVersionDO object to convert
     * @return An ApplicationVersion entity
     */
    public static ApplicationVersion toEntity(final ApplicationVersionDO appVersionDO) {
        ApplicationVersion appVersion = new ApplicationVersion();
        BeanUtils.copyProperties(appVersionDO, appVersion);
        return appVersion;
    }

    /**
     * Convert a list of ApplicationVersionDO objects to a list of ApplicationVersion entities.
     *
     * @param appDOs The list of ApplicationVersionDO objects to convert
     * @return A list of ApplicationVersion entities
     */
    public static List<ApplicationVersion> toEntity(final List<ApplicationVersionDO> appDOs) {
        List<ApplicationVersion> res = new ArrayList<>();
        for (ApplicationVersionDO appDO : appDOs) {
            ApplicationVersion app = toEntity(appDO);
            res.add(app);
        }
        return res;
    }

    /**
     * Convert an ApplicationVersion entity to an ApplicationVersionDO data object.
     *
     * @param appVersion The ApplicationVersion entity to convert
     * @return An ApplicationVersionDO data object
     */
    public static ApplicationVersionDO toDataObject(final ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = new ApplicationVersionDO();
        BeanUtils.copyProperties(appVersion, appVersionDO);
        return appVersionDO;
    }

    /**
     * Convert an ApplicationVersion entity to an ApplicationVersionDO data object for creation.
     *
     * @param appVersion The ApplicationVersion entity to convert
     * @return An ApplicationVersionDO data object for creation
     */
    public static ApplicationVersionDO toDataObjectForCreate(final ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = toDataObject(appVersion);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        appVersionDO.setCreatedAt(currentTime);
        appVersionDO.setUpdateAt(currentTime);
        appVersionDO.setId(id);

        return appVersionDO;
    }

    /**
     * Convert an ApplicationVersion entity to an ApplicationVersionDO data object for update.
     *
     * @param appVersion The ApplicationVersion entity to convert
     * @return An ApplicationVersionDO data object for update
     */
    public static ApplicationVersionDO toDataObjectForUpdate(final ApplicationVersion appVersion) {
        ApplicationVersionDO appVersionDO = toDataObject(appVersion);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        appVersionDO.setUpdateAt(currentTime);

        return appVersionDO;
    }
}

