package com.easysoftware.application.applicationpackage.vo;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPackageTagsVo {
    private String appVer;

    private String arch;
    
    private String dockerStr;
}
