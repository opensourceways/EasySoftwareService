package com.easysoftware.infrastructure.applicationpackage.gatewayimpl.dataobject;

import java.io.Serial;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("application_package")
public class ApplicationPackageDO {
    @Serial
    private static final long serialVersionUID = 1L;


    public String description;


    public String name;


    public String license;


    private String download;



    private String environment;
    

    private String installation;


    private String similarPkgs;


    private String dependencyPkgs;

    private String id;

    private Timestamp createAt;

    private Timestamp updateAt;
}
