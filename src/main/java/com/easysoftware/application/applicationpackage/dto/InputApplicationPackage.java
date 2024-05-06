package com.easysoftware.application.applicationpackage.dto;

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
public class InputApplicationPackage {
    /**
     * Description with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of description can not exceed 10000")
    private String description;

    /**
     * Name cannot be null and must have a maximum length of 255 characters.
     */
    @NotBlank(message = "name can not be null")
    @Size(max = 255, message = "the length of name can not exceed 255")
    private String name;

    /**
     * License with a maximum length of 1000 characters.
     */
    @Size(max = 1000, message = "the length of arch can not exceed 5000")
    private String license;
    /**
     * download with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of download can not exceed 10000")
    private String download;
    /**
     * environment with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of environment can not exceed 10000")
    private String environment;
    /**
     * installation with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of installation can not exceed 10000")
    private String installation;

    /**
     * Similar packages with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of similarPkgs can not exceed 10000")
    private String similarPkgs;

    /**
     * Dependency packages with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of dependencyPkgs can not exceed 10000")
    private String dependencyPkgs;

    /**
     * Category validated using the AppCategoryEnum class's isValidCategory method.
     */
    @EnumValue(enumClass = AppCategoryEnum.class, enumMethod = "isValidCategory")
    private String category;

    /**
     * type with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of type can not exceed 100")
    private String type;

    /**
     * iconUrl with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of iconUrl can not exceed 100")
    private String iconUrl;
    /**
     * appVer with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of appVer can not exceed 100")
    private String appVer;
    /**
     * osSupport with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of osSupport can not exceed 100")
    private String osSupport;
    /**
     * os with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of os can not exceed 100")
    private String os;
    /**
     * arch with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of arch can not exceed 100")
    private String arch;
    /**
     * maintainerId with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of maintainerId can not exceed 100")
    private String maintainerId;
    /**
     * maintainerEmail with a maximum length of 100 characters.
     */
    @Email
    @Size(max = 100, message = "the length of maintainerEmail can not exceed 100")
    private String maintainerEmail;
    /**
     * maintainerGiteeId with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of maintainerGiteeId can not exceed 100")
    private String maintainerGiteeId;
    /**
     * maintainerUpdateAt with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of maintainerUpdateAt can not exceed 100")
    private String maintainerUpdateAt;
    /**
     * securityLevel with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of securityLevel can not exceed 100")
    private String securityLevel;
    /**
     * safeLabel with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of safeLabel can not exceed 100")
    private String safeLabel;
    /**
     * downloadCount with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of downloadCount can not exceed 100")
    private String downloadCount;
    /**
     * appSize with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of appSize can not exceed 100")
    private String appSize;
    /**
     * srcRepo with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of srcRepo can not exceed 100")
    private String srcRepo;
    /**
     * srcDownloadUrl with a maximum length of 200 characters.
     */
    @URL
    @Size(max = 200, message = "the length of srcDownloadUrl can not exceed 200")
    private String srcDownloadUrl;
    /**
     * binDownloadUrl with a maximum length of 200 characters.
     */
    @URL
    @Size(max = 200, message = "the length of binDownloadUrl can not exceed 200")
    private String binDownloadUrl;
    /**
     * pkgId with a maximum length of 100 characters.
     */
    @Size(max = 100, message = "the length of pkgId can not exceed 100")
    private String pkgId;
    /**
     * imageTags with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of tags can not exceed 10000")
    private String imageTags;
    /**
     * imageUsage with a maximum length of 10000 characters.
     */
    @Size(max = 10000, message = "the length of usage can not exceed 10000")
    private String imageUsage;
}
