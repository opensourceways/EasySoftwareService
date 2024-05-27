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

package com.easysoftware.infrastructure.externalos.gatewayimpl.converter;

import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.infrastructure.externalos.gatewayimpl.dataobject.ExternalOsDO;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;

public final class ExternalOsConverter {
    // Private constructor to prevent instantiation of the MapConstant class
    private ExternalOsConverter() {
        // private constructor to hide the implicit public one
        throw new AssertionError("ExternalOsConverter class cannot be instantiated.");
    }

    /**
     * Converts an ExternalOsDO object to an ExternalOs entity.
     *
     * @param externalOsDO The ExternalOsDO object to convert.
     * @return The converted ExternalOs entity.
     */
    public static ExternalOs toEntity(final ExternalOsDO externalOsDO) {
        ExternalOs externalOs = new ExternalOs();
        BeanUtils.copyProperties(externalOsDO, externalOs);
        return externalOs;
    }

    /**
     * Converts a list of ExternalOsDO objects to a list of ExternalOs entities.
     *
     * @param exs The list of ExternalOsDO objects to convert.
     * @return A list of ExternalOs entities.
     */
    public static List<ExternalOs> toEntity(final List<ExternalOsDO> exs) {
        List<ExternalOs> res = new ArrayList<>();
        for (ExternalOsDO ex : exs) {
            ExternalOs e = toEntity(ex);
            res.add(e);
        }
        return res;
    }
}
