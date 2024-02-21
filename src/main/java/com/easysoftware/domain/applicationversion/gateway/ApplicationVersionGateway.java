package com.easysoftware.domain.applicationversion.gateway;

import java.util.List;

import com.easysoftware.application.applicationversion.dto.ApplicationVersionSearchCondition;
import com.easysoftware.domain.applicationversion.ApplicationVersion;


public interface ApplicationVersionGateway {
    boolean existApp(String name);
    boolean save(ApplicationVersion appVersion);
    boolean update(ApplicationVersion appVersion);
    boolean delete(List<String> names);
    List<ApplicationVersion> queryByName(ApplicationVersionSearchCondition condition);
}

