package com.easysoftware.common.exception.enumvalid;

public enum DomainTypeEnum {
    APPPKG("apppkg"),
    EPKGPKG("epkgpkg"),
    RPMPKG("rpmpkg"),
    ALL("all");

    private String alias;

    DomainTypeEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public static boolean isValidType(String alias) {
        for (DomainTypeEnum categoryEnum : DomainTypeEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }
}
