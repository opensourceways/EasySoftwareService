package com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.exception.enumvalid.TimeOrderEnum;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter.EPKGPackageConverter;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.power.common.util.StringUtil;

public class RPMPackageConverter {
    private static final Logger logger = LoggerFactory.getLogger(RPMPackageConverter.class);

    public static RPMPackage toEntity(RPMPackageDO rPMPkgDO) {
        RPMPackage rPMPkg = new RPMPackage();
        BeanUtils.copyProperties(rPMPkgDO, rPMPkg);
        return rPMPkg;
    }

    public static List<String> toColumn(List<RPMPackageDO> rPMPkgDOs, String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = RPMPackageDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (RPMPackageDO rPMPkgDO : rPMPkgDOs) {
                if (rPMPkgDO == null) {
                    continue;
                }
                Object obj = field.get(rPMPkgDO);
                if (! (obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(rPMPkgDO);
                res.add(value);
            }
        } catch (Exception e) {
            logger.error(MessageCode.EC00011.getMsgEn(), e);
        }
        return res;
    }

    public static List<RPMPackage> toEntity(List<RPMPackageDO> rPMPkgDOs) {
        List<RPMPackage> res = new ArrayList<>();
        for (RPMPackageDO rPMPkgDO : rPMPkgDOs) {
            RPMPackage rPMPkg = toEntity(rPMPkgDO);
            res.add(rPMPkg);
        }
        return res;
    }

    public static List<RPMPackageDetailVo> toDetail(List<RPMPackageDO> rPMPkgDOs) {
        List<RPMPackageDetailVo> res = new ArrayList<>();
        for (RPMPackageDO rpm: rPMPkgDOs) {
            RPMPackageDetailVo detail = new RPMPackageDetailVo();
            BeanUtils.copyProperties(rpm, detail);

            res.add(detail);
        }
        return res;
    }
    
    public static List<RPMPackageMenuVo> toMenu(List<RPMPackageDO> rPMPkgDOs) {
        List<RPMPackageMenuVo> res = new ArrayList<>();
        for (RPMPackageDO rpm: rPMPkgDOs) {
            RPMPackageMenuVo menu = new RPMPackageMenuVo();
            BeanUtils.copyProperties(rpm, menu);
            res.add(menu);
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
