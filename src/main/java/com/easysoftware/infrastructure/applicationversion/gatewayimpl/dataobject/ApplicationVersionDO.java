package com.easysoftware.infrastructure.applicationversion.gatewayimpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("application_version")
public class ApplicationVersionDO {
    @Serial
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "name")
    public String name;
    public String upHomepage;
    public String eulerHomepage;
    public String backend;
    public String upstreamVersion;
    public String openeulerVersion;
    public String ciVersion;
    public String status;
    private String id;
    private Timestamp createdAt;
    private Timestamp updateAt;
}
