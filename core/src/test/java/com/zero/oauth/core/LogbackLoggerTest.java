package com.zero.oauth.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import lombok.Getter;

public class LogbackLoggerTest {

    private CustomAppender appender;

    @Before
    public void setUp() {
        LoggerFactory.initialize("com.zero.oauth.core.test", Logger.LogbackLogger.class);
        LoggerContext context = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
        appender = new CustomAppender();
        appender.setContext(context);
        appender.start();
        getLogback().addAppender(appender);
    }

    private ch.qos.logback.classic.Logger getLogback() {
        org.slf4j.Logger logger = ((Logger.LogbackLogger) LoggerFactory.logger()).getLogger();
        return (ch.qos.logback.classic.Logger) logger;
    }

    @After
    public void tearDown() {
        appender.stop();
        getLogback().detachAppender(appender);
        LoggerFactory.destroy();
    }

    @Test
    public void test_info() {
        LoggerFactory.logger().info(new RuntimeException(), "test {0} abc", "1");
        ILoggingEvent last = this.appender.getLast();
        Assert.assertEquals("test 1 abc", last.getMessage());
        Assert.assertNull(last.getArgumentArray());
        Assert.assertTrue(((ThrowableProxy) last.getThrowableProxy()).getThrowable() instanceof RuntimeException);
        Assert.assertEquals("test 1 abc", last.getFormattedMessage());
        Assert.assertEquals(ch.qos.logback.classic.Level.INFO, last.getLevel());
    }

    @Test
    public void test_debug() {
        LoggerFactory.logger().debug("test");
        ILoggingEvent last = this.appender.getLast();
        Assert.assertEquals("test", last.getMessage());
        Assert.assertNull(last.getArgumentArray());
        Assert.assertNull(last.getThrowableProxy());
        Assert.assertEquals("test", last.getFormattedMessage());
        Assert.assertEquals(ch.qos.logback.classic.Level.DEBUG, last.getLevel());
    }

    @Test
    public void test_trace() {
        LoggerFactory.logger().trace(new RuntimeException(),"test abc");
        ILoggingEvent last = this.appender.getLast();
        Assert.assertEquals("test abc", last.getMessage());
        Assert.assertNull(last.getArgumentArray());
        Assert.assertTrue(((ThrowableProxy) last.getThrowableProxy()).getThrowable() instanceof RuntimeException);
        Assert.assertEquals("test abc", last.getFormattedMessage());
        Assert.assertEquals(ch.qos.logback.classic.Level.TRACE, last.getLevel());
    }

    @Test
    public void test_warn() {
        LoggerFactory.logger().warn(new RuntimeException(), "test {0} abc", "1");
        ILoggingEvent last = this.appender.getLast();
        Assert.assertEquals("test 1 abc", last.getMessage());
        Assert.assertTrue(((ThrowableProxy) last.getThrowableProxy()).getThrowable() instanceof RuntimeException);
        Assert.assertEquals("test 1 abc", last.getFormattedMessage());
        Assert.assertEquals(ch.qos.logback.classic.Level.WARN, last.getLevel());
    }

    @Test
    public void test_error() {
        LoggerFactory.logger().error("test {0} abc", "1");
        ILoggingEvent last = this.appender.getLast();
        Assert.assertEquals("test 1 abc", last.getMessage());
        Assert.assertEquals("test 1 abc", last.getFormattedMessage());
        Assert.assertNull(last.getArgumentArray());
        Assert.assertNull(last.getThrowableProxy());
        Assert.assertEquals(ch.qos.logback.classic.Level.ERROR, last.getLevel());
    }

    class CustomAppender extends AppenderBase<ILoggingEvent> {

        @Getter
        private ILoggingEvent last;

        @Override
        protected void append(ILoggingEvent eventObject) {
            this.last = eventObject;
        }

    }

}
