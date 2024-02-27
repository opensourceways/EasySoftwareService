package com.easysoftware.application.domainpackage.dto;
import org.hibernate.validator.constraints.URL;

import com.easysoftware.common.exception.enumvalid.AppCategoryEnum;
import com.easysoftware.common.exception.enumvalid.EnumValue;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDomainPackage {
    @Size(max = 10000, message = "the length of description can not exceed 10000")
    public String description;

    @NotBlank(message = "name can not be null")
    @Size(max = 255, message = "the length of name can not exceed 255")
    public String name;

    @Size(max = 1000, message = "the length of arch can not exceed 5000")
    public String license;

    @Size(max = 10000, message = "the length of download can not exceed 10000")
    private String download;
    @Size(max = 10000, message = "the length of environment can not exceed 10000")
    private String environment;
    @Size(max = 10000, message = "the length of installation can not exceed 10000")
    private String installation;

    @Size(max = 10000, message = "the length of similarPkgs can not exceed 10000")
    private String similarPkgs;

    @Size(max = 10000, message = "the length of dependencyPkgs can not exceed 10000")
    private String dependencyPkgs;

    @EnumValue(enumClass = AppCategoryEnum.class, enumMethod = "isValidCategory")
    private String appCategory;

    @Size(max = 100, message = "the length of type can not exceed 100")
    private String type;

    @Size(max = 100,  message = "the length of iconUrl can not exceed 100")
    private String iconUrl;

    @Size(max = 100,  message = "the length of appVer can not exceed 100")
    private String appVer;

    @Size(max = 100,  message = "the length of osSupport can not exceed 100")
    private String osSupport;

    @Size(max = 100,  message = "the length of os can not exceed 100")
    private String os;

    @Size(max = 100,  message = "the length of arch can not exceed 100")
    private String arch;

    @Size(max = 100,  message = "the length of maintainerId can not exceed 100")
    private String maintainerId;

    @Email
    @Size(max = 100,  message = "the length of maintainerEmail can not exceed 100")
    private String maintainerEmail;

    @Size(max = 100,  message = "the length of maintainerGiteeId can not exceed 100")
    private String maintainerGiteeId;

    @Size(max = 100,  message = "the length of maintainerUpdateAt can not exceed 100")
    private String maintainerUpdateAt;

    @Size(max = 100,  message = "the length of securityLevel can not exceed 100")
    private String securityLevel;

    @Size(max = 100,  message = "the length of safeLabel can not exceed 100")
    private String safeLabel;

    @Size(max = 100,  message = "the length of downloadCount can not exceed 100")
    private String downloadCount;

    @Size(max = 100,  message = "the length of appSize can not exceed 100")
    private String appSize;

    @Size(max = 100,  message = "the length of srcRepo can not exceed 100")
    private String srcRepo;

    @URL
    @Size(max = 200,  message = "the length of srcDownloadUrl can not exceed 200")
    private String srcDownloadUrl;

    @URL
    @Size(max = 200,  message = "the length of binDownloadUrl can not exceed 200")
    private String binDownloadUrl;
}
