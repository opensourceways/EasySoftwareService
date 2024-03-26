package com.easysoftware.application.applicationpackage.vo;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageMenuVo {
    private String category;
    private String name;
    private String description;
    private String iconUrl;
    private List<String> tags;
    private String id;
    private String pkgId;
}
