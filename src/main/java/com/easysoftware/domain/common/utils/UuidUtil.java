package com.easysoftware.domain.common.utils;

import java.util.UUID;

public class UuidUtil {

    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
    
}
