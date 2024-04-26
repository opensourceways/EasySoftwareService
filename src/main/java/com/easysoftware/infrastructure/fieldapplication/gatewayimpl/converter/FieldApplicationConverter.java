package com.easysoftware.infrastructure.fieldapplication.gatewayimpl.converter;


import java.util.ArrayList;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


import com.easysoftware.application.filedapplication.dto.InputFiledApplication;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;
import com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject.FieldApplicationDO;


public class FieldApplicationConverter {
    private static final Logger logger = LoggerFactory.getLogger(FieldApplicationConverter.class);

    public static FieldApplicationDO toDataObject(InputFiledApplication input) {
        FieldApplicationDO fieldApplicationDO = new FieldApplicationDO();
        BeanUtils.copyProperties(input, fieldApplicationDO);
        return fieldApplicationDO;
    }

    public static List<FiledApplicationVo> toVo(List<FieldApplicationDO> doList) {
        List<FiledApplicationVo> res = new ArrayList<>();
        for (FieldApplicationDO op : doList) {
            FiledApplicationVo vo = toVo(op);
            res.add(vo);
        }
        return res;
    }

    public static FiledApplicationVo toVo(FieldApplicationDO opDo) {
        FiledApplicationVo opVo = new FiledApplicationVo();
        BeanUtils.copyProperties(opDo, opVo);
        return opVo;
    }
}
