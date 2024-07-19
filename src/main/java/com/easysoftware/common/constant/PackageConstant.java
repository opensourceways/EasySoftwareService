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

package com.easysoftware.common.constant;

public final class PackageConstant {

    // Private constructor to prevent instantiation of the PackageConstant class
    private PackageConstant() {
        // private constructor to hide the implicit public one
        throw new AssertionError("PackageConstant class cannot be instantiated.");
    }

    /**
     * Maximum page number allowed.
     */
    public static final int MAX_PAGE_NUM = 100000;

    /**
     * Minimum page number allowed.
     */
    public static final int MIN_PAGE_NUM = 1;

    /**
     * zero constant.
     */
    public static final int ZERO = 0;

    /**
     * Maximum page size allowed.
     */
    public static final int MAX_PAGE_SIZE = 100;

    /**
     * Minimum page size allowed.
     */
    public static final int MIN_PAGE_SIZE = 5;

    /**
     * Maximum field length allowed.
     */
    public static final int MAX_FIELD_LENGTH = 1000;

    /**
     * VALID_STR_REG used to match input string.
     */
    public static final String VALID_STR_REG = "^$|^[\\u4E00-\\u9FA5A-Za-z0-9.()$\\-_+, ]+$";

    /**
     * VALID_MESSAGE, error message.
     */
    public static final String VALID_MESSAGE = "Null or string. String includes only letters, digits, and special "
            + "characters(_-+()$.,)";

    /**
     * HTTP_PREFIX, Referer pass prefix.
     */
    public static final String HTTP_PREFIX = "http://";

    /**
     * HTTPS_PREFIX, Referer pass prefix.
     */
    public static final String HTTPS_PREFIX = "https://";

    /**
     * table name of ApplicationPackageDO.
     */
    public static final String APP_PKG_TABLE = "application_package";

    /**
     * table name of ApplicationVersionDO.
     */
    public static final String APP_VER_TABLE = "application_version";

    /**
     * table name of ArchNumDO.
     */
    public static final String ARCH_NUM_TABLE = "arch_num";

    /**
     * table name of EPKGPackageDO.
     */
    public static final String EPKG_PKG_TABLE = "epkg_pkg";

    /**
     * table name of EulerLifeCycleDO.
     */
    public static final String EULER_LIFE_TABLE = "openeuler_lifecycle";

    /**
     * table name of FieldApplicationDO.
     */
    public static final String DOMAIN_PKG_TABLE = "domain_package";

    /**
     * table name of FieldPkgDO.
     */
    public static final String FIELD_PKG_TABLE = "field_package";

    /**
     * table name of OepkgDO.
     */
    public static final String OEPKG_TABLE = "oepkg";

    /**
     * table name of OperationCOnfigDO.
     */
    public static final String OPER_CONF_TABLE = "operation_config";

    /**
     * table name of RPMPackageDO.
     */
    public static final String RPM_PKG_TABLE = "rpm_pkg_base";

    /**
     * Change the category of rpm.
     */
    public static final String RPM_PKG_TABLE_CHANGE = "UPDATE rpm_pkg_base SET category = ? WHERE name = ?";

    /**
     * Change the category of application.
     */
    public static final String APPLICATION_TABLE_CHANGE = "UPDATE application_package SET category = ? WHERE name = ?";

    /**
     * Change the category of oepkg.
     */
    public static final String OEPKG_CHANGE = "UPDATE oepkg SET category = ? WHERE name = ?";

    /**
     * Change the category of epkg.
     */
    public static final String EPKG_CHANGE = "UPDATE epkg_pkg SET category = ? WHERE name = ?";

    /**
     * Change the category of filed.
     */
    public static final String FIELD_CHANGE = "UPDATE field_package SET category = ? WHERE name = ?";

    /**
     * Change the category of filed.
     */
    public static final String DOMAIN_CHANGE = "UPDATE domain_package SET category = ? WHERE name = ?";
    /**
     * Constan refresh timer.
     */
    public static final long TIMER = 10 * 60000;
}
