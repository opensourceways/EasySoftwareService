package com.easysoftware.common.constant;

import java.util.Map;

public class MapConstant {
    public static final String CATEGORY_BIGDATA = "bigdata";
    public static final String CATEGORY_AI = "ai";
    public static final String CATEGORY_STORAGE = "Storage";
    public static final String CATEGORY_CLOUD_NATIVE = "sig-CloudNative";
    public static final String CATEGORY_HPC = "sig-HPC";
    public static final String CATEGORY_OTHER = "Other";
    public static final String MAINTAINER_ID = "id";
    public static final String MAINTAINER_EMAIL = "email";
    public static final String MAINTAINER_GITEE_ID = "gitee_id";
    public static final Map<String, String> CATEGORY_MAP = Map.of(
            CATEGORY_BIGDATA, "大数据",
            CATEGORY_AI, "AI",
            CATEGORY_STORAGE, "分布式存储",
            CATEGORY_CLOUD_NATIVE, "云服务",
            CATEGORY_HPC, "HPC",
            CATEGORY_OTHER, "其他"
    );
    public static final Map<String, String> MAINTAINER = Map.of(
            MAINTAINER_ID, "openEuler Community",
            MAINTAINER_EMAIL, "contact@openeuler.io",
            MAINTAINER_GITEE_ID, "openeuler-ci-bot"
    );
}
