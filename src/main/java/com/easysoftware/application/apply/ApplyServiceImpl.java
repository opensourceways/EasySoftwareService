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
package com.easysoftware.application.apply;

import com.easysoftware.application.apply.dto.ApplyHandleConditon;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.apply.ApplyHandleRecord;
import com.easysoftware.domain.apply.gateway.ApplyGateway;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {

    /**
     * Resource for interacting with Apply  Gateway.
     */
    @Resource
    private final ApplyGateway applyGateway;

    /**
     * get apply handle records by appid.
     *
     * @param applyId The handle form content id.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> queryApplyHandleRecords(Long applyId) {

        ApplyHandleConditon applyHandleConditon = new ApplyHandleConditon();
        applyHandleConditon.setApplyId(applyId);
        List<ApplyHandleRecord> applyHandleRecords = applyGateway.queryApplyhandleRecords(applyHandleConditon);
        return ResultUtil.success(HttpStatus.OK, applyHandleRecords);
    }
}
