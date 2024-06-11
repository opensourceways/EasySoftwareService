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
package com.easysoftware.domain.eulerlifecycle.gateway;

import java.util.List;

import com.easysoftware.application.filedapplication.vo.EulerLifeCycleVo;

public interface EulerLifeCycleGateway {
    /**
     * Select all lifecycle of each euler operating system and return them as a list
     * of
     * EulerLifeCycleVo objects.
     *
     * @return A list of EulerLifeCycleVo objects containing all lifecycle of
     *         euler system
     */
    List<EulerLifeCycleVo> selectAll();
}
