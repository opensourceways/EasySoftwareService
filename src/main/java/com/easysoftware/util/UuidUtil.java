package com.easysoftware.util;

import java.util.UUID;

public class UuidUtil {

    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
    
}
