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

import com.easysoftware.application.applyform.dto.ApplyFormSearchAdminCondition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.easysoftware.application.applyform.dto.ApplyFormSearchMaintainerCondition;
import com.easysoftware.application.applyform.vo.ApplyFormContentVO;
import com.easysoftware.application.applyform.vo.ApplyFormSearchMaintainerVO;
import com.easysoftware.common.exception.NoneResException;
import com.easysoftware.common.exception.ParamErrorException;
import com.easysoftware.common.utils.ResultUtil;
import com.easysoftware.domain.applyform.gateway.ApplyFormGateway;

import jakarta.annotation.Resource;

@Service
public class ApplyFormServiceImpl implements ApplyFormService {

    /**
     * Resource for interacting with Apply Form Gateway.
     */
    @Resource
    private ApplyFormGateway applyFormGateway;

    /**
     * Search for apply form based on the provided search condition.
     *
     * @param condition The search condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchApplyFromByMaintainer(ApplyFormSearchMaintainerCondition condition) {
        Map<String, Object> res = new HashMap<>();
        if (condition.getName().equals("formPage")) {
            res = searchApplyFromByPage(condition);
        } else if (condition.getName().equals("formContent")) {
            res = searchApplyFromByApplyId(condition);
        }

        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search for apply form based on the provided search condition by admin.
     *
     * @param condition The search condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    @Override
    public ResponseEntity<Object> searchApplyFromByAdmin(ApplyFormSearchAdminCondition condition) {
        Map<String, Object> res = applyFormGateway.queryApplyFormByCondition(condition);
        return ResultUtil.success(HttpStatus.OK, res);
    }

    /**
     * Search for apply form based on the provided page condition.
     *
     * @param condition The page condition for querying apply form.
     * @return ResponseEntity<Object>.
     */
    private Map<String, Object> searchApplyFromByPage(ApplyFormSearchMaintainerCondition condition) {
        Map<String, Object> map = applyFormGateway.queryApplyFormByPage(condition);
        Long total = (Long) map.get("total");
        List<ApplyFormSearchMaintainerVO> appylFormVOs = (List<ApplyFormSearchMaintainerVO>) map.get("list");
        if (total == 0 || appylFormVOs.size() == 0) {
            throw new NoneResException("the apply forms is null");
        }

        Map<String, Object> res = new HashMap<>();
        res.put("total", total);
        res.put("list", appylFormVOs);

        return res;
    }

    /**
     * Search for apply form based on the provided search condition.
     *
     * @param condition The search condition for querying apply form by applyId.
     * @return ResponseEntity<Object>.
     */
    private Map<String, Object> searchApplyFromByApplyId(ApplyFormSearchMaintainerCondition condition) {
        if (condition.getApplyId() == null) {
            throw new ParamErrorException("the applyId can not be null");
        }

        Map<String, Object> res = applyFormGateway.queryApplyFormByApplyId(condition.getApplyId());

        Long total = (Long) res.get("total");

        List<ApplyFormContentVO> list = (List<ApplyFormContentVO>) res.get("list");

        if (total == 0 || list.size() == 0) {
            throw new ParamErrorException("the apply form content does not exist");
        }

        return res;

    }

}
