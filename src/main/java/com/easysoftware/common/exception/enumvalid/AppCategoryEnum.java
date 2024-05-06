package com.easysoftware.common.exception.enumvalid;

public enum AppCategoryEnum {
    /**
     * Enum representing the Big Data category.
     */
    BigData("大数据"),

    /**
     * Enum representing the AI category.
     */
    AI("AI"),

    /**
     * Enum representing the Distributed Storage category.
     */
    Storage("分布式存储"),

    /**
     * Enum representing the Database category.
     */
    Database("数据库"),

    /**
     * Enum representing the Cloud Services category.
     */
    Cloud("云服务"),

    /**
     * Enum representing the Other category.
     */
    Other("其他"),

    /**
     * Enum representing the High Performance Computing (HPC) category.
     */
    Hpc("HPC");

    /**
     * Alias for the category.
     */
    private final String alias;

    /**
     * Constructor for AppCategoryEnum with an alias.
     *
     * @param alias The alias for the category
     */
    AppCategoryEnum(final String alias) {
        this.alias = alias;
    }

    /**
     * Get the alias for the category.
     *
     * @return The alias for the category
     */
    public String getAlias() {
        return this.alias;
    }

    /**
     * Check if a given alias is a valid category.
     *
     * @param alias The alias to check
     * @return True if the alias represents a valid category, false otherwise
     */
    public static boolean isValidCategory(final String alias) {
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }
}
