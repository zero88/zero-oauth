package com.zero.oauth.core;

import org.junit.Before;

public class Log4j2LoggerTest {

    private Logger logger;

    @Before
    public void setUp() {
        this.logger = new Logger.Log4j2Logger("test");
    }

}
