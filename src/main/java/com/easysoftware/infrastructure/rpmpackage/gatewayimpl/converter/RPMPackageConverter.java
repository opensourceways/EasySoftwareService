package com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.common.exception.enumvalid.TimeOrderEnum;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.domain.rpmpackage.RPMPackage;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;
import com.power.common.util.StringUtil;

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

    public static <T, U> QueryWrapper<T> createQueryWrapper(T t, U u) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        
        Field[] fields = u.getClass().getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);

            Object value = null;
            try {
                value = field.get(u);
            } catch (Exception e) {
            }
            if (! (value instanceof String)) {
                continue;
            }

            String vStr = (String) value;
            if (StringUtils.isBlank(vStr)) {
                continue;
            }

            if ("timeOrder".equals(field.getName()) && TimeOrderEnum.DESC.getAlias().equals(vStr)) {
                wrapper.orderByDesc("rpm_update_at");
                continue;
            }
            if ("timeOrder".equals(field.getName()) && TimeOrderEnum.ASC.getAlias().equals(vStr)) {
                wrapper.orderByAsc("rpm_update_at");
                continue;
            }

            String undLine = StringUtil.camelToUnderline(field.getName());
            wrapper.eq(undLine, vStr);
        }
        return wrapper;
    }
}
