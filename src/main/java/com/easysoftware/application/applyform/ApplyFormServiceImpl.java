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
package com.easysoftware.application.applyform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applyform.dto.ApplyFormSearchMaintainerCondition;
import com.easysoftware.application.applyform.dto.ProcessApply;
import com.easysoftware.application.applyform.vo.ApplyFormContentVO;
import com.easysoftware.application.applyform.vo.ApplyFormSearchMaintainerVO;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.exception.UpdateException;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applyform.gateway.ApplyFormGateway;
import com.easysoftware.domain.collaboration.gateway.PackageStatusGateway;
import jakarta.annotation.Resource;

@Service
public class ApplyFormServiceImpl implements ApplyFormService {

    /**
     * Resource for interacting with Apply Form Gateway.
     */
    @Resource
    private ApplyFormGateway applyFormGateway;

    /**
     * Resource for interacting with Package Status Gateway.
     */
    @Resource
    private PackageStatusGateway pkgageStatusGateway;

    /**
     * Search for apply form based on the provided search condition.
     *
     * @param condition The search condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object>  searchApplyFromByMaintainer(ApplyFormSearchMaintainerCondition condition) {
        Map<String, Object> map = applyFormGateway.queryApplyFormByMaintainer(condition);
        Long total = (Long) map.get("total");
        List<ApplyFormSearchMaintainerVO> appylFormVOs = (List<ApplyFormSearchMaintainerVO>) map.get("list");
        if (total == 0 || appylFormVOs.size() == 0) {
            throw new NoneResException("the apply forms is null");
        }

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", appylFormVOs);

        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search for apply form based on the provided search condition.
     *
     * @param applyId The search condition for querying apply form by applyId.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchApplyFromByApplyId(Long applyId) {
        if (applyId == null) {
            throw new ParamErrorException("the applyId can not be null");
        }

        Map<String, Object> res = applyFormGateway.queryApplyFormByApplyId(applyId);

        Long total = (Long) res.get("total");

        List<ApplyFormContentVO> list = (List<ApplyFormContentVO>) res.get("list");

        if (total == 0 || list.size() == 0) {
            throw new ParamErrorException("the apply form content does not exist");
        }

        return ResultUtil.success(HttpStatus.OK, res);

    }

    /**
     * Process apply based on the provided condition.
     *
     * @param processApply The process condition for querying apply form by applyId.
     * @return ResponseEntity<Object>.
     */
    public ResponseEntity<Object> processApply(ProcessApply processApply) {
        boolean flag = applyFormGateway.processApply(processApply);
        if (!flag) {
            throw new UpdateException("process apply failed");
        }
        return ResultUtil.success(HttpStatus.OK);
    }

}
