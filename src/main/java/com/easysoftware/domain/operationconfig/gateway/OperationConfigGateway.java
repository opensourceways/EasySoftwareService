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

package com.easysoftware.domain.operationconfig.gateway;

import com.easysoftware.application.operationconfig.vo.OperationConfigVo;

import java.util.List;

public interface OperationConfigGateway {
    /**
     * Select all operation configurations and return them as a list of
     * OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation
     *         configurations
     */
    List<OperationConfigVo> selectAll();

    /**
     * Select all operation configurations with type = domainpage and return them as
     * a list of
     * OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation
     *         configurations
     */
    List<OperationConfigVo> selectRankingConfig();

    /**
     * Select all operation configurations with type = category_changes and return
     * them as
     * a list of
     * OperationConfigVo objects.
     *
     * @return A list of OperationConfigVo objects containing all operation
     *         configurations
     */
    List<OperationConfigVo> selectCategoryChanges();

}
