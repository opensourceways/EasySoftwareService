package com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.common.utils.UuidUtil;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

public class RPMPackageConverter {
    public static RPMPackage toEntity(RPMPackageDO rPMPkgDO) {
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(rPMPkgDO, rPMPkg);
        return rPMPkg;
    }

    public static List<RPMPackage> toEntity(List<RPMPackageDO> rPMPkgDOs) {
        List<RPMPackage> res = new ArrayList<>();
        for (RPMPackageDO rPMPkgDO : rPMPkgDOs) {
            RPMPackage rPMPkg = toEntity(rPMPkgDO);
            res.add(rPMPkg);
        }
        return res;
    }

    public static RPMPackageDO toDataObject(RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = new RPMPackageDO();
        BeanUtils.copyProperties(rPMPkg, rPMPkgDO);
        return rPMPkgDO;
    }

    public static RPMPackageDO toDataObjectForCreate(RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = toDataObject(rPMPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        rPMPkgDO.setCreateAt(currentTime);
        rPMPkgDO.setUpdateAt(currentTime);
        rPMPkgDO.setId(id);

        return rPMPkgDO;
    }

    public static RPMPackageDO toDataObjectForUpdate(RPMPackage rPMPkg) {
        RPMPackageDO rPMPkgDO = toDataObject(rPMPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        rPMPkgDO.setUpdateAt(currentTime);
       
        return rPMPkgDO;
    }
}
