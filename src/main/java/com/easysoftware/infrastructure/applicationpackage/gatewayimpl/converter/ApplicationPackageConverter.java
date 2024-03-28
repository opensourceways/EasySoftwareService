package com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.domainpackage.vo.DomainPackageMenuVo;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;

public class ApplicationPackageConverter {
    public static ApplicationPackage toEntity(ApplicationPackageDO appPkgDO) {
        ApplicationPackage appPkg = new ApplicationPackage();
        BeanUtils.copyProperties(appPkgDO, appPkg);
        return appPkg;
    }

    public static List<ApplicationPackageDetailVo> toDetail(List<ApplicationPackageDO> appPkgDOs) {
        List<ApplicationPackageDetailVo> res = new ArrayList<>();
        for (ApplicationPackageDO app: appPkgDOs) {
            res.add(toDetail(app));
        }
        return res;
    }

    public static ApplicationPackageDetailVo toDetail(ApplicationPackageDO app) {
        ApplicationPackageDetailVo detail = new ApplicationPackageDetailVo();
        BeanUtils.copyProperties(app, detail);
        return detail;
    }

    public static List<ApplicationPackageMenuVo> toMenu(List<ApplicationPackageDO> appPkgDOs) {
        List<ApplicationPackageMenuVo> res = new ArrayList<>();
        for (ApplicationPackageDO app: appPkgDOs) {
            ApplicationPackageMenuVo menu = new ApplicationPackageMenuVo();
            BeanUtils.copyProperties(app, menu);
            menu.setTags(List.of("IMAGE"));
            res.add(menu);
        }
        return res;
    }

    public static ApplicationPackageMenuVo toMenu(ApplicationPackageDO appPkgDO) {
        ApplicationPackageMenuVo menu = new ApplicationPackageMenuVo();
        BeanUtils.copyProperties(appPkgDO, menu);
        return menu;
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

    public static List<DomainPackageMenuVo> toDomainPackageMenuVo(List<ApplicationPackageMenuVo> appList) {
        List<DomainPackageMenuVo> menuList = new ArrayList<>();
        for (ApplicationPackageMenuVo app : appList) {
            DomainPackageMenuVo menu = toDomainPackageMenuVo(app);
            menuList.add(menu);
        }
        return menuList;
    }

    public static DomainPackageMenuVo toDomainPackageMenuVo(ApplicationPackageMenuVo app) {
        DomainPackageMenuVo menu = new DomainPackageMenuVo();
        BeanUtils.copyProperties(app, menu);
        menu.getPkgIds().put("IMAGE", app.getPkgId());
        menu.getTags().add("IMAGE");
        return menu;
    }
}

