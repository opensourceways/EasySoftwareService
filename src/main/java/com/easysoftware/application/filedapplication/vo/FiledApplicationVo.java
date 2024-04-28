package com.easysoftware.application.filedapplication.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiledApplicationVo {
    private String os;

    private String arch;

    private String name;

    private String version;

    private String category;

    private String iconUrl;

    private Set<String> tags;
    
    private Map<String,String> pkgIds;

    private String description;
}
