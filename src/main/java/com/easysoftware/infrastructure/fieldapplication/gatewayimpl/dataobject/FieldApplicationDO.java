package com.easysoftware.infrastructure.fieldapplication.gatewayimpl.dataobject;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableName;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
// TODO 修改表名
@TableName("domain_package")
public class FieldApplicationDO {
    @Serial
    private String os;

    private String arch;

    private String name;

    private String version;

    private String category;

    private String iconUrl;

    private String tags;
    
    private String pkgIds;

    private String description;
}
