package com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("application_version")
public class ApplicationVersionDO {
    @Serial
    private static final long serialVersionUID = 1L;
    public String name;
    public String version;
    public String homepage;
    public String backend;
    private String id;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
