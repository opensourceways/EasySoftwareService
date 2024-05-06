package com.easysoftware.common.exception.enumvalid;

public enum DomainTypeEnum {
    /**
     * Enum representing different domain types.
     */
    APPPKG("apppkg"),

    /**
     * Enum representing the EPKGPKG domain type.
     */
    EPKGPKG("epkgpkg"),

    /**
     * Enum representing the RPMPKG domain type.
     */
    RPMPKG("rpmpkg"),

    /**
     * Enum representing all domain types.
     */
    ALL("all");

    /**
     * Alias for the domain type.
     */
    private final String alias;

    /**
     * Constructor for DomainTypeEnum with an alias.
     *
     * @param alias The alias for the domain type
     */
    DomainTypeEnum(final String alias) {
        this.alias = alias;
    }

    /**
     * Get the alias for the domain type.
     *
     * @return The alias for the domain type
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Check if a given alias is a valid domain type.
     *
     * @param alias The alias to check
     * @return True if the alias represents a valid domain type, false otherwise
     */
    public static boolean isValidType(final String alias) {
        for (DomainTypeEnum categoryEnum : DomainTypeEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }
}
