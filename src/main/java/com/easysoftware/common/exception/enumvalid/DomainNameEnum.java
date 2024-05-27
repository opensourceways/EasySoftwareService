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

package com.easysoftware.common.exception.enumvalid;

public enum DomainNameEnum {
    /**
     * Enum representing the APPPKG domain.
     */
    APPPKG("apppkg"),

    /**
     * Enum representing the EPKGPKG domain.
     */
    EPKGPKG("epkgpkg"),

    /**
     * Enum representing the RPMPKG domain.
     */
    RPMPKG("rpmpkg"),

    /**
     * Enum representing all domains.
     */
    ALL("all");

    /**
     * Alias for the domain name.
     */
    private final String alias;

    /**
     * Constructor for DomainNameEnum with an alias.
     *
     * @param alias The alias for the domain name
     */
    DomainNameEnum(final String alias) {
        this.alias = alias;
    }

    /**
     * Get the alias for the domain name.
     *
     * @return The alias for the domain name
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Check if a given alias is a valid domain name type.
     *
     * @param alias The alias to check
     * @return True if the alias represents a valid domain name type, false otherwise
     */
    public static boolean isValidCategory(final String alias) {
        for (DomainNameEnum categoryEnum : DomainNameEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }
}
