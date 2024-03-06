package com.easysoftware.common.exception.enumvalid;

public enum DomainNameEnum {
    APPPKG("apppkg"),
    EPKGPKG("epkgpkg"),
    RPMPKG("rpmpkg"),
    ALL("all");

    private String alias;

    DomainNameEnum(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public static boolean isValidCategory(String alias) {
        for (DomainNameEnum categoryEnum : DomainNameEnum.values()) {
            if (categoryEnum.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }
}
