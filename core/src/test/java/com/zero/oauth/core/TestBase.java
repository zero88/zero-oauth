package com.zero.oauth.core;

import org.junit.BeforeClass;

public class TestBase {

    @BeforeClass
    public static void setUpClass() {
        LoggerFactory.initialize();
    }

}
