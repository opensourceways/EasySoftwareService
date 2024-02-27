package com.easysoftware.common.exception.enumvalid;

public enum AppCategoryEnum {
    BigData("大数据"),
    AI("AI"),
    Storage("分布式存储"),
    Database("数据库"),
    Cloud("云服务"),
    Hpc("HPC");

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
