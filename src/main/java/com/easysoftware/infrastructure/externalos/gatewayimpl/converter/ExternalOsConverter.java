package com.easysoftware.infrastructure.externalos.gatewayimpl.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.infrastructure.externalos.gatewayimpl.dataobject.ExternalOsDO;
import com.easysoftware.infrastructure.rpmpackage.gatewayimpl.converter.RPMPackageConverter;

public class ExternalOsConverter {
    private static final Logger logger = LoggerFactory.getLogger(ExternalOsConverter.class);


    public static ExternalOs toEntity(ExternalOsDO externalOsDO) {
        ExternalOs externalOs = new ExternalOs();
        BeanUtils.copyProperties(externalOsDO, externalOs);
        return externalOs;
    }

    public static List<ExternalOs> toEntity(List<ExternalOsDO> exs) {
        List<ExternalOs> res = new ArrayList<>();
        for (ExternalOsDO ex : exs) {
            ExternalOs e = toEntity(ex);
            res.add(e);
        }
        return res;
    }

}
