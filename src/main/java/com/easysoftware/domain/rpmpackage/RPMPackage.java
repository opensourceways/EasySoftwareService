package com.easysoftware.domain.rpmpackage;

import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RPMPackage {
    @Serial
    private static final long serialVersionUID = 1L;
  
    private String headerEnd;

    private String sizeInstalled;

    private String timeFile;

    private String sizePackage;

    private String description;

    private String checksumPkgid;

    private String locationHref;

    private String rpmBuildhost;

    private String checksumType;

    private String sizeArchive;

    private String rpmVendor;

    private String checksum;

    private String rpmGroup;

    private String headerStart;

    private String summary;

    private String versionRel;

    private String versionVer;

    private String packager;

    private String url;

    private String versionEpoch;

    private String rpmSourcerpm;

    private String rpmLicense;

    private String name;

    private String timeBuild;

    private String arch;

    private String osName;

    private String osVer;
    
    private String osType;

    private String files;

    private String provides;

    private String requires;

    private String baseUrl;

    private String rpmCategory;
}
