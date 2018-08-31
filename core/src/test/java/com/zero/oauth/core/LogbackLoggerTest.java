package com.zero.oauth.core;

import org.junit.Before;

public class LogbackLoggerTest {

    @Before
    public void setUp() {
        LoggerFactory.initialize(new Logger.LogbackLogger("test"));
    }

}
