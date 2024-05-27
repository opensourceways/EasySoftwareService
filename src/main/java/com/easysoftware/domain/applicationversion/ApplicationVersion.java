/* Copyright (c) 2024 openEuler Community
 EasySoftwareService is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

package com.easysoftware.domain.applicationversion;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class ApplicationVersion {
    /**
     * Class representing a specific entity with various properties.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Name of the entity.
     */
    private String name;

    /**
     * URL for the upstream homepage.
     */
    private String upHomepage;

    /**
     * URL for the EulerOS homepage.
     */
    private String eulerHomepage;

    /**
     * Backend information.
     */
    private String backend;

    /**
     * Upstream version details.
     */
    private String upstreamVersion;

    /**
     * OpenEuler version details.
     */
    private String openeulerVersion;

    /**
     * Continuous Integration (CI) version details.
     */
    private String ciVersion;

    /**
     * Status of the entity.
     */
    private String status;

    /**
     * Unique identifier of the entity.
     */
    private String id;

    /**
     * Version of openEuler os: openEuler-22.03.
     */
    private String eulerOsVersion;
}
