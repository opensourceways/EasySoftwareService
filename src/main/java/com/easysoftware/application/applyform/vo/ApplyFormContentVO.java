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
package com.easysoftware.application.applyform.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class ApplyFormContentVO {

    /**
     * Timestamp for last update.
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
     * apply status.
     */
    private String applyStatus;

    /**
     * applyId number.
     */
    private Long applyId;

    /**
     * applyIdString number.
     */
    private String applyIdString;

    /**
     * comment.
     */
    private String comment;

    /**
     * comment.
     */
    private Timestamp approvalTime;

    /**
     * get an ApplyFormContentVO entity updateAt field value.
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
     * set an ApplyFormContentVO entity updateAt field value.
     *
     * @param updateAt The ApplyFormContentVO entity  createAt field for set
     */
    public void setUpdateAt(Timestamp updateAt) {
        if (updateAt != null) {
            this.updateAt = (Timestamp) updateAt.clone();
        } else {
            this.updateAt = null;
        }
    }
}
