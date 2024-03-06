package com.easysoftware.infrastructure.externalos.gatewayimpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableName;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("external_os")
public class ExternalOsDO {
    @Serial
    private String id;

    private Timestamp createAt;

    private Timestamp updateAt;

    private String originOsName;

    private String originOsVer;

    private String originPkg;

    private String targetOsName;

    private String targetOsVer;

    private String targetPkg;
}
