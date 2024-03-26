package com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.QueryWrapperUtil;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.epkgpackage.EPKGPackage;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

public class EPKGPackageConverter {

    private static final Logger logger = LoggerFactory.getLogger(EPKGPackageConverter.class);

    public static List<EPKGPackageMenuVo> toMenu(List<EPKGPackageDO> rPMPkgDOs) {
        List<EPKGPackageMenuVo> res = new ArrayList<>();
        for (EPKGPackageDO rpm: rPMPkgDOs) {
            EPKGPackageMenuVo menu = toMenu(rpm);
            res.add(menu);
        }
        return res;
    }

    public static EPKGPackageMenuVo toMenu(EPKGPackageDO epkg) {
        EPKGPackageMenuVo menu = new EPKGPackageMenuVo();
        BeanUtils.copyProperties(epkg, menu);
        return menu;
    }

    public static List<String> toColumn(List<EPKGPackageDO> epkgDOs, String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = EPKGPackageDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (EPKGPackageDO epkgDO : epkgDOs) {
                if (epkgDO == null) {
                    continue;
                }
                Object obj = field.get(epkgDO);
                if (! (obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(epkgDO);
                res.add(value);
            }
        } catch (Exception e) {
            logger.error(MessageCode.EC00011.getMsgEn(), e);
        }
        return res;
    }

    public static List<EPKGPackageDetailVo> toDetail(List<EPKGPackageDO> rPMPkgDOs) {
        List<EPKGPackageDetailVo> res = new ArrayList<>();
        for (EPKGPackageDO rpm: rPMPkgDOs) {
            EPKGPackageDetailVo detail = new EPKGPackageDetailVo();
            BeanUtils.copyProperties(rpm, detail);
            res.add(detail);
        }
        return res;
    }

    public static EPKGPackageDO toDataObject(EPKGPackage epkg) {
        EPKGPackageDO epkgPackageDO = new EPKGPackageDO();
        BeanUtils.copyProperties(epkg, epkgPackageDO);
        return epkgPackageDO;
    }

    public static EPKGPackageDO toDataObjectForCreate(EPKGPackage epkg) {
        EPKGPackageDO epkgPackageDO = toDataObject(epkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String id = UuidUtil.getUUID32();
        epkgPackageDO.setCreateAt(currentTime);
        epkgPackageDO.setUpdateAt(currentTime);
        epkgPackageDO.setId(id);

        return epkgPackageDO;
    }
    
    public static EPKGPackageDO toDataObjectForUpdate(EPKGPackage rPMPkg) {
        EPKGPackageDO rPMPkgDO = toDataObject(rPMPkg);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        rPMPkgDO.setUpdateAt(currentTime);

        return rPMPkgDO;
    }
}
