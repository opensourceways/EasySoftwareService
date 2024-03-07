package com.easysoftware.domain.applicationversion.gateway;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.domain.applicationversion.ApplicationVersion;
import com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject.ApplicationVersionDO;


public interface ApplicationVersionGateway {
    boolean existApp(String name);
    boolean save(ApplicationVersion appVersion);
    boolean update(ApplicationVersion appVersion);
    boolean delete(List<String> names);
    Map<String, Object> queryByName(ApplicationVersionSearchCondition condition);
    Collection<ApplicationVersionDO> convertBatch(Collection<String> dataObject);
}

