/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.application.domainpackage.converter;

import org.springframework.beans.BeanUtils;

import com.easysoftware.application.applicationpackage.dto.ApplicationPackageSearchCondition;
import com.easysoftware.application.domainpackage.dto.DomainSearchCondition;
import com.easysoftware.application.epkgpackage.dto.EPKGPackageSearchCondition;
import com.easysoftware.application.rpmpackage.dto.RPMPackageSearchCondition;

public final class DomainPackageConverter {

    // Private constructor to prevent instantiation of the utility class
    private DomainPackageConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("Cannot instantiate DomainPackageConverter class");
    }

    /**
     * Converts a DomainSearchCondition object to an ApplicationPackageSearchCondition object.
     *
     * @param con The DomainSearchCondition to convert.
     * @return An ApplicationPackageSearchCondition object.
     */
    public static ApplicationPackageSearchCondition toApp(final DomainSearchCondition con) {
        ApplicationPackageSearchCondition appCon = new ApplicationPackageSearchCondition();
        BeanUtils.copyProperties(con, appCon);
        appCon.setName("");
        appCon.setTimeOrder("");
        return appCon;
    }

    /**
     * Converts a DomainSearchCondition object to an RPMPackageSearchCondition object.
     *
     * @param con The DomainSearchCondition to convert.
     * @return An RPMPackageSearchCondition object.
     */
    public static RPMPackageSearchCondition toRpm(final DomainSearchCondition con) {
        RPMPackageSearchCondition rPMCon = new RPMPackageSearchCondition();
        BeanUtils.copyProperties(con, rPMCon);
        rPMCon.setName("");
        return rPMCon;
    }

    /**
     * Converts a DomainSearchCondition object to an EPKGPackageSearchCondition object.
     *
     * @param con The DomainSearchCondition to convert.
     * @return An EPKGPackageSearchCondition object.
     */
    public static EPKGPackageSearchCondition toEpkg(final DomainSearchCondition con) {
        EPKGPackageSearchCondition eCon = new EPKGPackageSearchCondition();
        BeanUtils.copyProperties(con, eCon);
        eCon.setName("");
        return eCon;
    }

}
