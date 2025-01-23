package com.easysoftware.common.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public final class VersionUtil {
    // Private constructor to prevent instantiation of the utility class
    private VersionUtil() {
        // private constructor to hide the implicit public one
        throw new AssertionError("VersionUtil class cannot be instantiated.");
    }

    /**
     * compare two version.
     * @param version1 version1.
     * @param version2 version2.
     * @return int.
     */
    public static int compareVersion(String version1, String version2) {
        PkgVersion ver1 = getVersions(version1);
        PkgVersion ver2 = getVersions(version2);

        // 比较主版本
        if (ver1.getMajorVersion() > ver2.getMajorVersion()) {
            return 1;
        } else if (ver1.getMajorVersion() < ver2.getMajorVersion()) {
           return -1;
        }

        // 比较次版本
        if (ver1.getMinorVersion() > ver2.getMinorVersion()) {
            return 1;
        } else if (ver1.getMinorVersion() < ver2.getMinorVersion()) {
            return -1;
        }

        // 比较修正号
        if (ver1.getRevision() > ver2.getRevision()) {
            return 1;
        } else if (ver1.getRevision() < ver2.getRevision()) {
            return -1;
        }

        return 0;
    }

    // rpm的版本为`x.y.z-a`，`x, y, z`分别是主版本，次版本，修正号，`a`是release
    private static PkgVersion getVersions(String version) {
        if (StringUtils.isBlank(version)) {
            return PkgVersion.EMPTY;
        }
        String[] splits = version.split("-");
        if (splits == null || splits.length != 2) {
            return PkgVersion.EMPTY;
        }
        String ver = splits[0];
        String[] vers = ver.split("\\.");

        if (vers == null || vers.length == 0) {
            return PkgVersion.EMPTY;
        }

        PkgVersion pkgVersion = new PkgVersion();
        for (int i = 0; i < vers.length; i++) {
            if (i == 0) {
                pkgVersion.setMajorVersion(Integer.valueOf(vers[0]));
            } else if (i == 1) {
                pkgVersion.setMinorVersion(Integer.valueOf(vers[1]));
            } else if (i == 2) {
                pkgVersion.setRevision(Integer.valueOf(vers[2]));
            }
        }
        return pkgVersion;
    }

    @Getter
    @Setter
    private static final class PkgVersion {
        /**
         * 主版本.
         */
        private Integer majorVersion = -1;


        private Integer minorVersion = -1;
        /**
         * 修正号.
         */
        private Integer revision = -1;

        /**
         * 空对象.
         */
        private static final PkgVersion EMPTY = new PkgVersion();
    }
}
