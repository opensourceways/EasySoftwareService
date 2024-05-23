package com.easysoftware.application.externalos;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.application.externalos.dto.InputExternalOs;
import com.easysoftware.common.entity.MessageCode;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.externalos.ExternalOs;
import com.easysoftware.domain.externalos.gateway.ExternalOsGateway;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ExternalOsServiceImpl implements ExternalOsService {
    /**
     * Gateway for external operating system operations.
     */
    @Resource
    private ExternalOsGateway externalOsGateway;

    /**
     * Search for package mappings based on the specified search conditions.
     *
     * @param condition The search conditions to filter package mappings.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchPkgMap(final ExternalOsSearchCondiiton condition) {
        Map<String, Object> res = externalOsGateway.queryPkgMap(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
