package com.easysoftware.domain.externalos.gateway;

import java.util.List;
import java.util.Map;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.domain.externalos.ExternalOsUnique;

public interface ExternalOsGateway {
    Map<String, Object> queryPkgMap(ExternalOsSearchCondiiton condition);
    boolean existExternalOs(String id);
    boolean delete(String id);
    boolean existExternalOs(ExternalOsUnique uni);
    boolean save(ExternalOs ex);
    boolean update(ExternalOs ex);
}
