package com.easysoftware.application.domainpackage.vo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class DomainPackageMenuVo {
    private String category;
    private String name;
    private String description;
    private String iconUrl;
    private Set<String> tags;
    private Map<String, String> pkgIds;

    public DomainPackageMenuVo() {
        this.tags = new HashSet<>();
        this.pkgIds = new HashMap<>();
        this.pkgIds.put("RPM", "");
        this.pkgIds.put("EPKG", "");
        this.pkgIds.put("IMAGE", "");
    }
}
