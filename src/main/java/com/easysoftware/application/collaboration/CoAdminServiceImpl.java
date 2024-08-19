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
package com.easysoftware.application.collaboration;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.collaboration.dto.PackageSearchCondition;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.collaboration.gateway.PackageStatusGateway;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoAdminServiceImpl implements CoAdminService {
    /**
     * Resource for interacting with package status Gateway.
     */
    @Resource
    private PackageStatusGateway pkgStatusGateway;

    /**
     * Searches for packages based on the specified search conditions.
     *
     * @param condition The search conditions to filter packages.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> queryAdminPackages(PackageSearchCondition condition) {
        Map<String, Object> res = pkgStatusGateway.queryAllByCondition(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }
}
