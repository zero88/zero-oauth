package com.zero.oauth.core;

import org.junit.Before;

public class Log4j2LoggerTest {

    @Before
    public void setUp() {
        LoggerFactory.initialize(new Logger.Log4j2Logger("test"));
    }

}
