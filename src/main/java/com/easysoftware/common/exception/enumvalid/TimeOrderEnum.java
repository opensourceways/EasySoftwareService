package com.easysoftware.common.exception.enumvalid;

public enum TimeOrderEnum {
    /**
     * Enum representing different time order types.
     */
    DESC("desc"),

    /**
     * Enum representing the ascending time order type.
     */
    ASC("asc");

    /**
     * Alias for the time order.
     */
    private final String alias;

    /**
     * Constructor for TimeOrderEnum with an alias.
     *
     * @param alias The alias for the time order
     */
    TimeOrderEnum(final String alias) {
        this.alias = alias;
    }

    /**
     * Get the alias for the time order.
     *
     * @return The alias for the time order
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Check if a given alias is a valid time order type.
     *
     * @param alias The alias to check
     * @return True if the alias represents a valid time order type, false otherwise
     */
    public static boolean isValidCategory(final String alias) {
        for (TimeOrderEnum categoryEnum : TimeOrderEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }

}
