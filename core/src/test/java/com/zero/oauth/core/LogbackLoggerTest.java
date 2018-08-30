package com.zero.oauth.core;

import org.junit.Before;

public class LogbackLoggerTest {

    private Logger logger;

    @Before
    public void setUp() {
        this.logger = new Logger.LogbackLogger("test");
    }

}
