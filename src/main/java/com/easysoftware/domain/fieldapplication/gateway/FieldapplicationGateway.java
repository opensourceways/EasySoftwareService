package com.easysoftware.domain.fieldapplication.gateway;


import java.util.Map;

import com.easysoftware.application.filedapplication.dto.FiledApplicationSerachCondition;


public interface FieldapplicationGateway {
    Map<String, Object> queryAll(FiledApplicationSerachCondition condition);

}
