package com.easysoftware.common.exception.enumvalid;

public enum AppCategoryEnum {
    BigData("bigdata"),
    AI("ai"),
    Storage("storage"),
    Database("database"),
    Cloud("cloud"),
    Hpc("hpc");

    private String alias;

    AppCategoryEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public static boolean isValidCategory(String alias) {
        for (AppCategoryEnum categoryEnum : AppCategoryEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }
}
