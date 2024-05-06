package com.easysoftware.domain.fieldapplication.gateway;


import java.util.List;
import java.util.Map;

import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;
import com.easysoftware.application.filedapplication.vo.FiledApplicationVo;


public interface FieldapplicationGateway {
    Map<String, Object> queryMenuByPage(FiledApplicationSerachCondition condition);
    List<FiledApplicationVo> queryVoList();
    Map<String, List<String>> queryColumn(List<String> columns);

}
