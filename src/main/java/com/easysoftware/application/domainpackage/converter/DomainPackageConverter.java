package com.easysoftware.application.domainpackage.converter;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;

public class DomainPackageConverter {
    public static ApplicationPackageSearchCondition toApp(DomainSearchCondition con) {
        ApplicationPackageSearchCondition appCon = new ApplicationPackageSearchCondition();
        BeanUtils.copyProperties(con, appCon);
        return appCon;
    }

    public static RPMPackageSearchCondition toRpm(DomainSearchCondition con) {
        RPMPackageSearchCondition rPMCon = new RPMPackageSearchCondition();
        BeanUtils.copyProperties(con, rPMCon);
        rPMCon.setName("");
        return rPMCon;
    }

    public static EPKGPackageSearchCondition toEpkg(DomainSearchCondition con) {
        EPKGPackageSearchCondition eCon = new EPKGPackageSearchCondition();
        BeanUtils.copyProperties(con, eCon);
        eCon.setName("");
        return eCon;
    }
}
