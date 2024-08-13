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
package com.easysoftware.domain.apply;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.sql.Timestamp;

@Getter
@Setter
public class ApplyHandleRecord {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id.
     */
    private Long id;

    /**
     * create time.
     */

    private Timestamp createdAt;

    /**
     * apply Id.
     */
    private String applyId;

    /**
     * recordId.
     */
    private Long recordId;

    /**
     * maintainer.
     */
    private String maintainer;

    /**
     * administrator.
     */
    private String administrator;

    /**
     * applyStatus.
     */
    private Byte applyStatus;


    /**
     * get an ApplyHandleRecord entity createdAt field value.
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
     * set an ApplyHandleRecord entity createAt field value.
     *
     * @param createAt The ApplicationPackageDO entity  createAt field for set
     */
    public void setCreatedAt(Timestamp createAt) {
        if (this.createdAt != null) {
            this.createdAt = (Timestamp) createAt.clone();
        } else {
            this.createdAt = null;
        }
    }
}
