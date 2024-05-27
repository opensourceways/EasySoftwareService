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

package com.easysoftware.infrastructure.externalos.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.sql.Timestamp;

@Data
@TableName("external_os")
public class ExternalOsDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private String id;

    /**
     * Timestamp for creation.
     */
    private Timestamp createAt;

    /**
     * Timestamp for last update.
     */
    private Timestamp updateAt;

    /**
     * Original operating system name.
     */
    private String originOsName;

    /**
     * Original operating system version.
     */
    private String originOsVer;

    /**
     * Original package information.
     */
    private String originPkg;

    /**
     * Target operating system name.
     */
    private String targetOsName;

    /**
     * Target operating system version.
     */
    private String targetOsVer;

    /**
     * Target package information.
     */
    private String targetPkg;

    /**
     * get an ExternalOsDO entity createAt field value.
     *
     * @return An Timestamp entity
     */
    public Timestamp getCreateAt() {
        if (this.createAt != null) {
            return (Timestamp) this.createAt.clone();
        } else {
            return null;
        }
    }

    /**
     * get an ExternalOsDO entity updateAt field value.
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
     * set an ExternalOsDO entity createAt field value.
     *
     * @param createAt The ApplicationPackageDO entity  createAt field for set
     */
    public void setCreateAt(Timestamp createAt) {
        if (this.createAt != null) {
            this.createAt = (Timestamp) createAt.clone();
        } else {
            this.createAt = null;
        }
    }

    /**
     * set an ExternalOsDO entity updateAt field value.
     *
     * @param updateAt The ApplicationPackageDO entity  updateAt field for set
     */
    public void setUpdateAt(Timestamp updateAt) {
        if (this.updateAt != null) {
            this.updateAt = (Timestamp) updateAt.clone();
        } else {
            this.updateAt = null;
        }
    }
}
