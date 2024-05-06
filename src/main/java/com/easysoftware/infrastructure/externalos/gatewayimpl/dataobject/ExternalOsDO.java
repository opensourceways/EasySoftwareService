package com.easysoftware.infrastructure.externalos.gatewayimpl.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
