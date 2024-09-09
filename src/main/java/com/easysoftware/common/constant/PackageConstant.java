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
    public static final int MAX_PAGE_NUM = 2000000;

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
     * official repo addres.
     */
    public static final String OFFICIAL_REPO = "gitee.com/src-openEuler/";

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
     * table name of ApplyhandleRecordsDO.
     */
    public static final String APPLY_HANDLE_RECORDS = "apply_handle_records";

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

    /**
     * Constan aggerate page size.
     */
    public static final long AGGREATE_PAGE_SIZE = 30;

    /**
     * Constan software num.
     */
    public static final long SOFTWARE_NUM = 82782;

    /**
     * package status keyword format.
     */
    public static final String KEY_WORD_FORMAT = " AND %s.keyword:%s";

    /**
     * table name of ApplyForm.
     */
    public static final String APPLY_FORM_TABLE = "apply_form";

    /**
     * cve status.
     */
    public static final String CVE_ALL_FIXED = "有CVE且全部修复";

    /**
     * cve status.
     */
    public static final String CVE_ALL_NO_FIXED = "有CVE且全部未修复";

    /**
     * cve status.
     */
    public static final String CVE_SOME_FIXED = "有CVE部分修复";

    /**
     * cve status.
     */
    public static final String NO_CVE = "没有CVE问题";

    /**
     * issue status.
     */
    public static final String ISSUE_ALL_NO_FIXED = "没有Issue修复";

    /**
     * package version status.
     */
    public static final String LATEST_VERSION = "正常版本";

    /**
     * package version status.
     */
    public static final String OUTDATED_VERSION = "落后版本";

    /**
     * package update status.
     */
    public static final String PR_NO_UPDATED = "没有PR提交";

    /**
     * package maintainenance status.
     */
    public static final String NO_MAINTAINENANCE = "没有人维护";

    /**
     * package maintainenance status.
     */
    public static final String LACK_OF_MAINTAINENANCE = "缺人维护";

    /**
     * package maintainenance status.
     */
    public static final String HEALTH = "健康";

    /**
     * package maintainenance status.
     */
    public static final String ACTIVE = "活跃";

    /**
     * package maintainenance status.
     */
    public static final String INACTIVE = "静止";

    /**
     * apply status approved.
     */
    public static final String APPLY_APPROVED = "APPROVED";

    /**
     * apply status open.
     */
    public static final String APPLY_OPEN = "OPEN";

    /**
     * apply status rejected.
     */
    public static final String APPLY_REJECTED = "REJECTED";

    /**
     * apply status revoked.
     */
    public static final String APPLY_REVOKED = "REVOKED";

    /**
     * applytId in apply_form table.
     */
    public static final String APPLY_FORM_ID = "apply_id";

    /**
     * maintainer in apply_form table.
     */
    public static final String APPLY_FORM_MAINTAINER = "maintainer";

    /**
     * administrator in apply_form table.
     */
    public static final String APPLY_FORM_ADMIN = "administrator";

    /**
     * parameter sep in apply_form table.
     */
    public static final String PARAMETER_SEP = ",";

    /**
     * VALID_APPROVAL_REG used to match input string.
     */
    public static final String VALID_APPROVAL_REG = APPLY_APPROVED + "|" + APPLY_REJECTED;

    /**
     * cve metric in apply_form table.
     */
    public static final String PKG_CVE_METRIC = "cveStatus";

    /**
     * version metric in apply_form table.
     */
    public static final String PKG_VERSION_METRIC = "versionStatus";

    /**
     * VALID_METRIC_REG used to match input string.
     */
    public static final String VALID_METRIC_REG = PKG_CVE_METRIC + "|" + PKG_VERSION_METRIC;

     /**
     * official repo addrres prefix.
     */
    public static final String OFFICIAL_REPO_PREFIX = HTTPS_PREFIX + OFFICIAL_REPO;

    /**
     * VALID_BODY_STR_REG used to match input string.
     */
    public static final String VALID_BODY_STR_REG = "^[^<>%&$]+$";

    /**
     * gitee id field.
     */
    public static final String LOGIN_NAME = "loginName";

    /**
     * authing user name field.
     */
    public static final String USER_NAME = "userName";

    /**
     * cve status suggestion.
     */
    public static final String CVE_SUGGESTION = "请完成CVE修复";

    /**
     * version status suggestion.
     */
    public static final String VERSION_SUGGESTION = "请检查版本是否合适";
}
