/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.domain.epkgpackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EPKGPackage {
    /**
     * Class representing a software package with various attributes.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the software package.
     */
    private String name;

    /**
     * Version of the software package.
     */
    private String version;

    /**
     * Operating system for which the package is intended.
     */
    private String os;

    /**
     * Architecture of the package.
     */
    private String arch;

    /**
     * Category to which the package belongs.
     */
    private String category;

    /**
     * Timestamp indicating when the package was last updated in the EPKG system.
     */
    private String epkgUpdateAt;

    /**
     * Source repository information for the package.
     */
    private String srcRepo;

    /**
     * Size of the EPKG package.
     */
    private String epkgSize;

    /**
     * Binary download URL for the package.
     */
    private String binDownloadUrl;

    /**
     * Source code download URL for the package.
     */
    private String srcDownloadUrl;

    /**
     * Summary or brief description of the package.
     */
    private String summary;

    /**
     * Operating system support details for the package.
     */
    private String osSupport;

    /**
     * Repository where the package is hosted.
     */
    private String repo;

    /**
     * Type of repository hosting the package.
     */
    private String repoType;

    /**
     * Installation instructions for the package.
     */
    private String installation;

    /**
     * Detailed description of the package.
     */
    private String description;

    /**
     * Software packages required by this package.
     */
    private String requires;

    /**
     * Functionality provided by the package.
     */
    private String provides;

    /**
     * Packages that conflict with this package.
     */
    private String conflicts;

    /**
     * Changelog detailing changes made to the package.
     */
    private String changeLog;

    /**
     * ID of the maintainer responsible for the package.
     */
    private String maintainerId;

    /**
     * Email address of the maintainer.
     */
    private String maintainerEmail;

    /**
     * Gitee ID of the maintainer.
     */
    private String maintainerGiteeId;

    /**
     * Timestamp indicating when maintainer details were last updated.
     */
    private String maintainerUpdateAt;

    /**
     * Status of the maintainer.
     */
    private String maintainerStatus;

    /**
     * Upstream source for the package.
     */
    private String upStream;

    /**
     * Security-related information for the package.
     */
    private String security;

    /**
     * Similar packages related to this package.
     */
    private String similarPkgs;

    /**
     * Files included in the package.
     */
    private String files;

    /**
     * Number of downloads the package has received.
     */
    private String downloadCount;

    /**
     * Unique identifier of the package.
     */
    private String pkgId;

    /**
     * Subpath or additional path information related to the package.
     */
    private String subPath;

    /**
     * License.
     */
    private String license;

}
