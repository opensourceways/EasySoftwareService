package com.easysoftware.application.rpmpackage.dto;

import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputRPMPackage {
    @Size(max = 255, message = "the length of headerEnd can not exceed 255")
    private String headerEnd;

    @Size(max = 255, message = "the length of sizeInstalled can not exceed 255")
    private String sizeInstalled;
    
    @Size(max = 255, message = "the length of timeFile can not exceed 255")
    private String timeFile;
    
    @Size(max = 255, message = "the length of sizePackage can not exceed 255")
    private String sizePackage;

    @Size(max = 6000, message = "the length of description can not exceed 6000")
    private String description;
    
    @Size(max = 255, message = "the length of checksumPkgid can not exceed 255")
    private String checksumPkgid;
    
    @Size(max = 255, message = "the length of locationHref can not exceed 255")
    private String locationHref;
    
    @Size(max = 255, message = "the length of rpmBuildhost can not exceed 255")
    private String rpmBuildhost;
    
    @Size(max = 255, message = "the length of checksumType can not exceed 255")
    private String checksumType;
    
    @Size(max = 255, message = "the length of sizeArchive can not exceed 255")
    private String sizeArchive;
    
    @Size(max = 255, message = "the length of rpmVendor can not exceed 255")
    private String rpmVendor;
    
    @Size(max = 255, message = "the length of checksum can not exceed 255")
    private String checksum;
    
    @Size(max = 255, message = "the length of rpmGroup can not exceed 255")
    private String rpmGroup;

    @Size(max = 255, message = "the length of headerStart can not exceed 255")
    private String headerStart;
    
    @Size(max = 255, message = "the length of summary can not exceed 255")
    private String summary;
    
    @Size(max = 255, message = "the length of versionRel can not exceed 255")
    private String versionRel;
    
    @Size(max = 255, message = "the length of versionVer can not exceed 255")
    private String versionVer;
    
    @Size(max = 255, message = "the length of packager can not exceed 255")
    private String packager;
    
    @Size(max = 255, message = "the length of url can not exceed 255")
    private String url;
    
    @Size(max = 255, message = "the length of versionEpoch can not exceed 255")
    private String versionEpoch;
    
    @Size(max = 255, message = "the length of rpmSourcerpm can not exceed 255")
    private String rpmSourcerpm;

    @Size(max = 1000, message = "the length of rpmLicense can not exceed 255")
    private String rpmLicense;

    @Size(max = 255, message = "the length of name can not exceed 255")
    private String name;
    
    @Size(max = 255, message = "the length of timeBuild can not exceed 255")
    private String timeBuild;
    
    @Size(max = 255, message = "the length of arch can not exceed 255")
    private String arch;

    @Size(max = 255, message = "the length of osName can not exceed 255")
    private String osName;
    
    @Size(max = 255, message = "the length of osVer can not exceed 255")
    private String osVer;
    
    @Size(max = 255, message = "the length of osType can not exceed 255")
    private String osType;

    @Size(max = 100_0000, message = "the length of requires can not exceed 100_0000")
    private String requires;
    
    @Size(max = 100_0000, message = "the length of provides can not exceed 100_0000")
    private String provides;

    @Size(max = 100_0000, message = "the length of files can not exceed 100_0000")
    private String files;

    @Size(max = 255, message = "the length of baseUrl can not exceed 255")
    private String baseUrl;

    @Size(max = 255, message = "the length of rpmCategory can not exceed 255")
    private String rpmCategory;

    @Size(max = 255, message = "the length of id can not exceed 255")
    private String id;
}
