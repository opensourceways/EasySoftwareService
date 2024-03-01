package com.easysoftware.application.domainpackage.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomainPackageMenuVo {
    private String category;
    private String name;
    private String description;
    private String iconUrl;
    private List<String> tags;
}
