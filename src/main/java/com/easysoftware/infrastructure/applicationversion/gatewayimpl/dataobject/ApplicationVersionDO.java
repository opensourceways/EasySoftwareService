package com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.sql.Timestamp;

@Getter
@Setter
@TableName("application_version")
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
