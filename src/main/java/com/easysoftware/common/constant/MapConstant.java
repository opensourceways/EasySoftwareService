package com.easysoftware.common.constant;

import java.util.Map;

public final class MapConstant {

    // Private constructor to prevent instantiation of the MapConstant class
    private MapConstant() {
        // private constructor to hide the implicit public one
        throw new AssertionError("MapConstant class cannot be instantiated.");
    }

    /**
     * Category constant for Big Data.
     */
    public static final String CATEGORY_BIGDATA = "bigdata";

    /**
     * Category constant for Artificial Intelligence.
     */
    public static final String CATEGORY_AI = "ai";

    /**
     * Category constant for Storage.
     */
    public static final String CATEGORY_STORAGE = "Storage";

    /**
     * Category constant for Cloud Native.
     */
    public static final String CATEGORY_CLOUD_NATIVE = "sig-CloudNative";

    /**
     * Category constant for High Performance Computing (HPC).
     */
    public static final String CATEGORY_HPC = "sig-HPC";

    /**
     * Category constant for Other.
     */
    public static final String CATEGORY_OTHER = "Other";

    /**
     * Maintainer ID key.
     */
    public static final String MAINTAINER_ID = "id";

    /**
     * Maintainer email key.
     */
    public static final String MAINTAINER_EMAIL = "email";

    /**
     * Maintainer Gitee ID key.
     */
    public static final String MAINTAINER_GITEE_ID = "gitee_id";

    /**
     * Map of category mappings.
     */
    public static final Map<String, String> CATEGORY_MAP = Map.of(
            CATEGORY_BIGDATA, "大数据",
            CATEGORY_AI, "AI",
            CATEGORY_STORAGE, "分布式存储",
            CATEGORY_CLOUD_NATIVE, "云服务",
            CATEGORY_HPC, "HPC",
            CATEGORY_OTHER, "其他"
    );

    /**
     * Map of maintainer information.
     */
    public static final Map<String, String> MAINTAINER = Map.of(
            MAINTAINER_ID, "openEuler Community",
            MAINTAINER_EMAIL, "contact@openeuler.io",
            MAINTAINER_GITEE_ID, "openeuler-ci-bot"
    );

}
