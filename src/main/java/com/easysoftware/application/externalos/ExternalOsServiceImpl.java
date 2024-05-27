/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.application.externalos;

import com.easysoftware.application.externalos.dto.ExternalOsSearchCondiiton;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.externalos.gateway.ExternalOsGateway;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
