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

package com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easysoftware.common.constant.PackageConstant;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.sql.Timestamp;

@Getter
@Setter
@TableName(PackageConstant.APP_VER_TABLE)
public class ApplicationVersionDO {
    /**
     * Serializable class with a defined serial version UID.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the entity (ID: name).
     */
    @TableId(value = "name")
    private String name;

    /**
     * Homepage for updates.
     */
    private String upHomepage;

    /**
     * Homepage for Euler.
     */
    private String eulerHomepage;

    /**
     * Backend information.
     */
    private String backend;

    /**
     * Upstream version details.
     */
    private String upstreamVersion;

    /**
     * OpenEuler version details.
     */
    private String openeulerVersion;

    /**
     * Continuous Integration version.
     */
    private String ciVersion;

    /**
     * Status of the entity.
     */
    private String status;

    /**
     * ID of the entity.
     */
    private String id;

    /**
     * Timestamp for creation.
     */
    private Timestamp createdAt;

    /**
     * Timestamp for last update.
     */
    private Timestamp updateAt;

    /**
     * Version of openEuler os: openEuler-22.03.
     */
    private String eulerOsVersion;

    /**
     * type of pkg.
     */
    private String type;

    /**
     * get an ApplicationVersionDO entity createdAt field value.
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
     * get an ApplicationVersionDO entity updateAt field value.
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
     * set an ApplicationVersionDO entity createAt field value.
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

    /**
     * set an ApplicationVersionDO entity createAt field value.
     *
     * @param updateAt The ApplicationPackageDO entity  createAt field for set
     */
    public void setUpdateAt(Timestamp updateAt) {
        if (this.updateAt != null) {
            this.updateAt = (Timestamp) updateAt.clone();
        } else {
            this.updateAt = null;
        }
    }
}
