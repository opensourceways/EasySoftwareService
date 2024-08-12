package com.easysoftware.common.account;

public final class UerPermissionDef {
    /**
     * user permission - easysoftwareread.
     */
    public static final String USER_PERMISSION_READ = "easysoftwareread";

    /**
     * user permission - easysoftwareadmin.
     */
    public static final String COLLABORATION_PERMISSION_ADMIN = "easysoftwareadmin";

    private UerPermissionDef() {
        throw new RuntimeException("Not supported for instantiation");
    }
}
