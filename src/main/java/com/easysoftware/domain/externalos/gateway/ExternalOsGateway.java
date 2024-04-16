package com.easysoftware.domain.externalos.gateway;

import java.util.List;
import java.util.Map;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.domain.externalos.ExternalOsUnique;

public interface ExternalOsGateway {
    Map<String, Object> queryPkgMap(ExternalOsSearchCondiiton condition);
    boolean existExternalOs(String id);
    int delete(List<String> ids);
    boolean existExternalOs(ExternalOsUnique uni);
    boolean save(ExternalOs ex);
    int update(ExternalOs ex);
}
