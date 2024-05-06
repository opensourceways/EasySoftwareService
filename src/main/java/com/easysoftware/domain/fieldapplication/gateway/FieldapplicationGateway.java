package com.easysoftware.domain.fieldapplication.gateway;


import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;

import java.util.List;
import java.util.Map;


public interface FieldapplicationGateway {
    Map<String, Object> queryMenuByPage(FiledApplicationSerachCondition condition);
    List<FiledApplicationVo> queryVoList();
    Map<String, List<String>> queryColumn(List<String> columns);


}
