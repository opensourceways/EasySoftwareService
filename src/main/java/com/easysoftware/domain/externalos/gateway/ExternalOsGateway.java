package com.easysoftware.domain.externalos.gateway;

import java.util.List;
import java.util.Map;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.domain.externalos.ExternalOs;

public interface ExternalOsGateway {
    Map<String, Object> queryPkgMap(ExternalOsSearchCondiiton condition);
}
