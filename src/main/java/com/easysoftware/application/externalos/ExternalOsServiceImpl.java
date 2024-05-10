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

    /**
     * Delete package mappings associated with the specified list of IDs.
     *
     * @param ids List of IDs of package mappings to be deleted.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> deletePkgMap(final List<String> ids) {
        int mark = externalOsGateway.delete(ids);
        String msg = String.format(Locale.ROOT, "the number of deleted : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

    /**
     * Insert a new package mapping using the provided input data.
     *
     * @param input The input data for creating a new package mapping.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> insertPkgMap(final InputExternalOs input) {
        // 若数据库中已经存在该数据，则请求失败
        if (StringUtils.isNotBlank(input.getId())) {
            return ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0002);
        }

        ExternalOs ex = new ExternalOs();
        BeanUtils.copyProperties(input, ex);

        boolean succeed = externalOsGateway.save(ex);
        return succeed ? ResultUtil.success(HttpStatus.OK)
                : ResultUtil.fail(HttpStatus.BAD_REQUEST, MessageCode.EC0006);
    }

    /**
     * Update an existing package mapping with the provided input data.
     *
     * @param input The input data for updating an existing package mapping.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> updatePkgMap(final InputExternalOs input) {
        ExternalOs ex = new ExternalOs();
        BeanUtils.copyProperties(input, ex);

        int mark = externalOsGateway.update(ex);
        String msg = String.format(Locale.ROOT, "the number of updated : %d", mark);
        return ResultUtil.success(HttpStatus.OK, msg);
    }

}
