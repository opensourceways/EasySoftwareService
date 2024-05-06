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

}
