package com.zero.oauth.core;

import org.junit.Before;

public class JdkLoggerTest {

    private Logger logger;

    @Before
    public void setUp() {
        this.logger = new Logger.JdkLogger("test");
    }

}
