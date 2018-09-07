package com.zero.oauth.core;

import org.junit.Before;

public class Log4j2LoggerTest {

    @Before
    public void setUp() {
        LoggerFactory.initialize("com.zero.oauth.core.test", Logger.Log4j2Logger.class);
    }

}
