package com.zero.oauth.core;

import org.junit.Before;

public class LogbackLoggerTest {

    @Before
    public void setUp() {
        LoggerFactory.initialize("com.zero.oauth.core.test", Logger.LogbackLogger.class);
    }

}
