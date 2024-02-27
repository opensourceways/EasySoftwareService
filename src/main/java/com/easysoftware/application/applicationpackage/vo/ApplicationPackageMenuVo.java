package com.easysoftware.application.applicationpackage.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageMenuVo {
    private String appCategory;
    private String name;
    private String description;
    private String iconUrl;
    private String type;
}
