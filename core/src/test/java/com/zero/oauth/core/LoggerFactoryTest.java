package com.zero.oauth.core;

import java.util.logging.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LoggerFactoryTest {

    @After
    public void tearDown() {
        LoggerFactory.destroy();
    }

    @Test(expected = IllegalStateException.class)
    public void test_getInstance_withoutInit() {
        LoggerFactory.instance();
    }

    @Test
    public void test_initDefaultLogger() {
        LoggerFactory.initialize("jdkLogger");
        Assert.assertTrue(LoggerFactory.logger() instanceof Logger.JdkLogger);
        Assert.assertEquals("jdkLogger", LoggerFactory.logger().getName());
    }

    @Test
    public void test_initJdkLogger_ByClass() {
        LoggerFactory.initialize("jdkLogger", Logger.JdkLogger.class);
        Assert.assertTrue(LoggerFactory.logger() instanceof Logger.JdkLogger);
        Assert.assertEquals("jdkLogger", LoggerFactory.logger().getName());
    }

    @Test
    public void test_initJdkLogger_ByInstance() {
        LoggerFactory.initialize(new Logger.JdkLogger("jdkLogger"));
        Assert.assertTrue(LoggerFactory.logger() instanceof Logger.JdkLogger);
        Assert.assertEquals("jdkLogger", LoggerFactory.logger().getName());
    }

    @Test
    public void test_initLog4jLogger_ByClass() {
        LoggerFactory.initialize("log4j2", Logger.Log4j2Logger.class);
        Assert.assertTrue(LoggerFactory.logger() instanceof Logger.Log4j2Logger);
        Assert.assertEquals("log4j2", LoggerFactory.logger().getName());
    }

    @Test
    public void test_initLogbackLogger_ByClass() {
        LoggerFactory.initialize("logback", Logger.LogbackLogger.class);
        Assert.assertTrue(LoggerFactory.logger() instanceof Logger.LogbackLogger);
        Assert.assertEquals("logback", LoggerFactory.logger().getName());
    }

    @Test
    public void test_initCustomLogger() {
        LoggerFactory.initialize("customLogger", MockLogger.class);
        Assert.assertTrue(LoggerFactory.logger() instanceof MockLogger);
        Assert.assertEquals("customLogger", LoggerFactory.logger().getName());
    }

    @Test
    public void test_initCustomLogger_ByInstance() {
        LoggerFactory.initialize(new MockLogger("customLogger"));
        Assert.assertTrue(LoggerFactory.logger() instanceof MockLogger);
        Assert.assertEquals("customLogger", LoggerFactory.logger().getName());
    }

    @Getter
    @RequiredArgsConstructor
    static class MockLogger implements Logger {

        private final String name;

        @Override
        public void log(Level level, String msg) {}

        @Override
        public void log(Level level, String msgPattern, Object... params) {}

        @Override
        public void log(Level level, Throwable throwable, String msg) {}

        @Override
        public void log(Level level, Throwable throwable, String msgPattern, Object... params) {}

        @Override
        public <L> L mapLevel(Level level) {
            return null;
        }

    }

}
