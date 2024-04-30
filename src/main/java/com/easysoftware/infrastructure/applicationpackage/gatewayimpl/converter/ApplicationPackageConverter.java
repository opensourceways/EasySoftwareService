package com.easysoftware.infrastructure.applicationpackage.gatewayimpl.converter;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.easysoftware.application.applicationpackage.vo.ApplicationPackageDetailVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageMenuVo;
import com.easysoftware.application.applicationpackage.vo.ApplicationPackageTagsVo;
import com.easysoftware.application.domainpackage.vo.DomainPackageMenuVo;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.UuidUtil;
import com.easysoftware.domain.applicationpackage.ApplicationPackage;
import com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject.ApplicationPackageDO;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;

public class ApplicationPackageConverter {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationPackageConverter.class);

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

        public static List<ApplicationPackageTagsVo> aggregateByTags(List<ApplicationPackageDO> appPkgDOs) {
        
        List<ApplicationPackageTagsVo> appTags = new ArrayList<>();

        for(ApplicationPackageDO app : appPkgDOs){
            ApplicationPackageTagsVo tag = new ApplicationPackageTagsVo();
            tag.setAppVer(app.getAppVer());
            tag.setArch(app.getArch());
            tag.setDockerStr("docker pull openeuler/".concat(app.getName()).concat(":").concat(app.getAppVer()));
            appTags.add(tag);
        }

        Map<String, ApplicationPackageTagsVo> agrMap = new HashMap<>();
        for (ApplicationPackageTagsVo tag : appTags){
            if(agrMap.containsKey(tag.getAppVer())){
                // 该tag下已经存在，开始合并
                ApplicationPackageTagsVo mergeTag = agrMap.get(tag.getAppVer());
                String mergedArch = mergeTag.getArch().concat(",").concat(tag.getArch());
                mergeTag.setArch(mergedArch);
            }else{
                agrMap.put(tag.getAppVer(), tag);
            }   
        }

        List<ApplicationPackageTagsVo> resTags = new ArrayList<>();

        for (Map.Entry<String, ApplicationPackageTagsVo> entry : agrMap.entrySet()) {  
            
            ApplicationPackageTagsVo agrTag = entry.getValue();  
            resTags.add(agrTag);
        }

        return resTags;
    }

    public static List<ApplicationPackageMenuVo> toMenu(List<ApplicationPackageDO> appPkgDOs) {
        List<ApplicationPackageMenuVo> res = new ArrayList<>();
        for (ApplicationPackageDO app: appPkgDOs) {
            ApplicationPackageMenuVo menu = new ApplicationPackageMenuVo();
            BeanUtils.copyProperties(app, menu);
            
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

    public static List<String> toColumn(List<ApplicationPackageDO> appDOs, String column) {
        List<String> res = new ArrayList<>();
        try {
            Field field = ApplicationPackageDO.class.getDeclaredField(column);
            field.setAccessible(true);
            for (ApplicationPackageDO appDo : appDOs) {
                if (appDo == null) {
                    continue;
                }
                Object obj = field.get(appDo);
                if (! (obj instanceof String)) {
                    continue;
                }
                String value = (String) field.get(appDo);
                res.add(value);
            }
        } catch (Exception e) {
            logger.error(MessageCode.EC00011.getMsgEn(), e);
        }
        return res;
    }
}

