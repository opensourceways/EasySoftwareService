package com.easysoftware.infrastructure.epkgpackage.gatewayimpl.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.epkgpackage.vo.EPKGPackageDetailVo;
import com.easysoftware.application.epkgpackage.vo.EPKGPackageMenuVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageDetailVo;
import com.easysoftware.application.rpmpackage.vo.RPMPackageMenuVo;
import com.easysoftware.infrastructure.epkgpackage.gatewayimpl.dataobject.EPKGPackageDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.dataobject.RPMPackageDO;

public class EPKGPackageConverter {
    public static List<EPKGPackageMenuVo> toMenu(List<EPKGPackageDO> rPMPkgDOs) {
        List<EPKGPackageMenuVo> res = new ArrayList<>();
        for (EPKGPackageDO rpm: rPMPkgDOs) {
            EPKGPackageMenuVo menu = new EPKGPackageMenuVo();
            BeanUtils.copyProperties(rpm, menu);
            res.add(menu);
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
    
}
