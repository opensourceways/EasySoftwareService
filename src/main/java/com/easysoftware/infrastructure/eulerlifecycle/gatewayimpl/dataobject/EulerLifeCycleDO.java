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

package com.easysoftware.infrastructure.eulerlifecycle.gatewayimpl.dataobject;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("openeuler_lifecycle")
public class EulerLifeCycleDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * openeuelr operating sysytem.
     */
    private String os;

    /**
     * release time of os.
     */
    private String releaseTime;

    /**
     * maintenance duration of euler os.
     */
    private String maintenanceDuration;

    /**
     * planned Maintenance endtime of euler os.
     */
    private String plannedMaintenanceEndtime;

    /**
     * actual Maintenance endtime of euler os.
     */
    private String actualMaintenanceEndtime;

    /**
     * currente status of euler os.
     */
    private String status;

    /**
     * remarks of current os.
     */
    private String remarks;
}
