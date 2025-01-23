package com.easysoftware.common.utils;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;


public class VersionUtilTest {
    @Test
    void test_version() {
        String v1 = null;
        String v2 = "1.0.0-a1";
        assertEquals(VersionUtil.compareVersion(v1, v2), -1);

        v2 = null;
        v1 = "1.0.0-a1";
        assertEquals(VersionUtil.compareVersion(v1, v2), 1);

        v2 = null;
        v1 = null;
        assertEquals(VersionUtil.compareVersion(v1, v2), 0);

        v2 = "1.0.0-a1";
        v1 = "1.0.0-a1";
        assertEquals(VersionUtil.compareVersion(v1, v2), 0);

        v2 = "1.0.1-a1";
        v1 = "1.0.0-a1";
        assertEquals(VersionUtil.compareVersion(v1, v2), -1);

        v1 = "1.0.1-a1";
        v2 = "1.0.0-a1";
        assertEquals(VersionUtil.compareVersion(v1, v2), 1);

        v1 = "1.0-a1";
        v2 = "1.0.0-a1";
        assertEquals(VersionUtil.compareVersion(v1, v2), -1);
    }
}
