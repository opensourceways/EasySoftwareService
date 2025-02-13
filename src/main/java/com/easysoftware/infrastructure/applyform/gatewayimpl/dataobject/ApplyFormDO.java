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
package com.easysoftware.infrastructure.applyform.gatewayimpl.dataobject;

import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easysoftware.common.constant.PackageConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(PackageConstant.APPLY_FORM_TABLE)
public class ApplyFormDO {

    /**
     * Timestamp for creation.
     */
    private Timestamp createdAt;

    /**
     * Package name.
     */
    private String repo;

    /**
     * Timestamp for last update.
     */
    private Timestamp updateAt;

    /**
     * Application type.
     */
    private String metric;

    /**
     * Modified state.
     */
    private String metricStatus;

    /**
     * Description of the application form.
     */
    private String description;

    /**
     * maintainer of the entity.
     */
    private String maintainer;

    /**
     * administrator of the entity.
     */
    private String administrator;

    /**
     * comment of the process apply.
     */
    private String comment;

    /**
     * Approval status.
     */
    @TableField(value = "apply_status")
    private String applyStatus;

    /**
     * Application number.
     */
    @TableId(value = "apply_id")
    private Long applyId;

    /**
     * Timestamp for approval.
     */
    private Timestamp approvalTime;


    /**
     * get an ApplyFormDO entity updateAt field value.
     *
     * @return An Timestamp entity
     */
    public Timestamp getUpdateAt() {
        if (this.updateAt != null) {
            return (Timestamp) this.updateAt.clone();
        } else {
            return null;
        }
    }


    /**
     * get an ApplyFormDO entity updateAt field value.
     *
     * @return An Timestamp entity
     */
    public Timestamp getCreatedAt() {
        if (this.createdAt != null) {
            return (Timestamp) this.createdAt.clone();
        } else {
            return null;
        }
    }


    /**
     * set an ApplyFormDO entity createAt field value.
     *
     * @param createdAt The ApplicationPackageDO entity  createAt field for set
     */
    public void setCreatedAt(Timestamp createdAt) {
        if (createdAt != null) {
            this.createdAt = (Timestamp) createdAt.clone();
        } else {
            this.createdAt = null;
        }
    }


    /**
     * set an ApplyFormDO entity createAt field value.
     *
     * @param updateAt The ApplicationPackageDO entity  createAt field for set
     */
    public void setUpdateAt(Timestamp updateAt) {
        if (updateAt != null) {
            this.updateAt = (Timestamp) updateAt.clone();
        } else {
            this.updateAt = null;
        }
    }
}
