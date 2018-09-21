package com.zero.oauth.core;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestBase {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    @BeforeClass
    public static void setUpClass() {
        LoggerFactory.initialize("com.zero.oauth.test");
    }

    @AfterClass
    public static void teardownClass() {
        LoggerFactory.destroy();
    }

    public static boolean isWin() {
        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isUnix() {
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }

    public static boolean isSolaris() {
        return OS.contains("sunos");
    }

}
