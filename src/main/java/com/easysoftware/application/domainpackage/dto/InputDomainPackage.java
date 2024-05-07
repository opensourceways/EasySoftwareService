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
    /**
     * Description with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of description can not exceed 10000")
    private String description;

    /**
     * Name that cannot be null and has a maximum length of 255 characters.
     */
    @NotBlank(message = "name can not be null")
    @Size(max = 255, message = "the length of name can not exceed 255")
    private String name;

    /**
     * Description with a license length of 10000 characters.
     */
    @Size(max = 1000, message = "the length of arch can not exceed 1000")
    private String license;

    /**
     * Download information with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of download can not exceed 10000")
    private String download;

    /**
     * Environment details with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of environment can not exceed 10000")
    private String environment;

    /**
     * Installation instructions with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of installation can not exceed 10000")
    private String installation;

    /**
     * Similar packages information with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of similarPkgs can not exceed 10000")
    private String similarPkgs;

    /**
     * Dependency packages information with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of dependencyPkgs can not exceed 10000")
    private String dependencyPkgs;

    /**
     * Category with validation against AppCategoryEnum's valid categories.
     */
    @EnumValue(enumClass = AppCategoryEnum.class, enumMethod = "isValidCategory")
    private String category;

    /**
     * Type with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of type can not exceed 100")
    private String type;


    /**
     * URL to the icon of maximum length 100 characters.
     */
    @Size(max = 100, message = "the length of iconUrl can not exceed 100")
    private String iconUrl;

    /**
     * Application version with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of appVer can not exceed 100")
    private String appVer;

    /**
     * Operating system support information with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of osSupport can not exceed 100")
    private String osSupport;

    /**
     * Operating system details with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of os can not exceed 100")
    private String os;

    /**
     * Architecture details with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of arch can not exceed 100")
    private String arch;

    /**
     * Maintainer ID with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of maintainerId can not exceed 100")
    private String maintainerId;

    /**
     * Email address of the maintainer with a maximum length of 100 characters.
     */
    @Email
    @Size(max = 100, message = "the length of maintainerEmail can not exceed 100")
    private String maintainerEmail;

    /**
     * Gitee ID of the maintainer with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of maintainerGiteeId can not exceed 100")
    private String maintainerGiteeId;

    /**
     * Last update timestamp for the maintainer with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of maintainerUpdateAt can not exceed 100")
    private String maintainerUpdateAt;

    /**
     * Security level information with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of securityLevel can not exceed 100")
    private String securityLevel;

    /**
     * Safe label information with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of safeLabel can not exceed 100")
    private String safeLabel;

    /**
     * Download count information with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of downloadCount can not exceed 100")
    private String downloadCount;

    /**
     * Application size information with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of appSize can not exceed 100")
    private String appSize;

    /**
     * Source repository URL with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of srcRepo can not exceed 100")
    private String srcRepo;

    /**
     * URL for downloading the source code with a maximum length of 200 characters.
     */
    @URL
    @Size(max = 200, message = "the length of srcDownloadUrl can not exceed 200")
    private String srcDownloadUrl;

    /**
     * URL for downloading the binary with a maximum length of 200 characters.
     */
    @URL
    @Size(max = 200, message = "the length of binDownloadUrl can not exceed 200")
    private String binDownloadUrl;

}
