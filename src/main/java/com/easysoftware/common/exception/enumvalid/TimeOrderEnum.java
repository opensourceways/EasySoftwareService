package com.easysoftware.common.exception.enumvalid;

public enum TimeOrderEnum {
    DESC("desc"),
    ASC("asc");

    private String alias;

    TimeOrderEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public static boolean isValidCategory(String alias) {
        for (TimeOrderEnum categoryEnum : TimeOrderEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }
}
