package com.zero.oauth.core;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lombok.Getter;

public class JdkLoggerTest {

    private MockHandler handler;

    @Before
    public void setUp() {
        LoggerFactory.initialize("com.zero.oauth.core.test");
        handler = new MockHandler();
        handler.setLevel(Level.ALL);
        java.util.logging.Logger logger = getLogger();
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
    }

    @After
    public void tearDown() {
        getLogger().removeHandler(handler);
        LoggerFactory.destroy();
    }

    private java.util.logging.Logger getLogger() {
        Logger jdkLogger = LoggerFactory.logger();
        return ((Logger.JdkLogger) jdkLogger).getLogger();
    }

    @Test
    public void test_info() {
        LoggerFactory.logger().info("test");
        Assert.assertEquals(Level.INFO, this.handler.getLast().getLevel());
        Assert.assertEquals("test", this.handler.getLast().getMessage());
    }

    @Test
    public void test_debug() {
        LoggerFactory.logger().debug(new RuntimeException(), "test {0}", "xtz");
        Assert.assertNotNull(this.handler.getLast());
        Assert.assertEquals(Level.FINE, this.handler.getLast().getLevel());
        Assert.assertEquals("test xtz", this.handler.getLast().getMessage());
        Assert.assertTrue(this.handler.getLast().getThrown() instanceof RuntimeException);
    }

    @Test
    public void test_trace() {
        LoggerFactory.logger().trace("test {0}", "xtz");
        Assert.assertEquals(Level.FINEST, this.handler.getLast().getLevel());
        Assert.assertEquals("test {0}", this.handler.getLast().getMessage());
        Assert.assertArrayEquals(new String[] {"xtz"}, this.handler.getLast().getParameters());
    }

    @Test
    public void test_warn() {
        LoggerFactory.logger().warn("test {0} abc", "1");
        Assert.assertEquals(Level.WARNING, this.handler.getLast().getLevel());
        Assert.assertEquals("test {0} abc", this.handler.getLast().getMessage());
        Assert.assertArrayEquals(new String[] {"1"}, this.handler.getLast().getParameters());
    }

    @Test
    public void test_error() {
        LoggerFactory.logger().error(new RuntimeException(), "test {0} abc", "1");
        Assert.assertEquals(Level.SEVERE, this.handler.getLast().getLevel());
        Assert.assertEquals("test 1 abc", this.handler.getLast().getMessage());
        Assert.assertTrue(this.handler.getLast().getThrown() instanceof RuntimeException);
    }

    class MockHandler extends Handler {

        @Getter
        LogRecord last;

        @Override
        public void publish(LogRecord record) {
            this.last = record;
        }

        @Override
        public void flush() {}

        @Override
        public void close() throws SecurityException {}

    }

}
