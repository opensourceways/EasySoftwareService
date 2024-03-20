package com.easysoftware.application.rpmpackage.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPMPackageDomainVo {
    private String category;
    private String description;
    private String name;
    private List<String> tags;
}
