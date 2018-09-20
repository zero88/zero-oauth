package com.zero.oauth.core;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.utils.Strings;

import lombok.Getter;

public class Log4j2LoggerTest {

    private CustomAppender appender;

    @Before
    public void setUp() {
        LoggerFactory.initialize("com.zero.oauth.core.test", Logger.Log4j2Logger.class);
        appender = CustomAppender.createAppender("CustomAppender");
        appender.start();
        org.apache.logging.log4j.core.Logger logger = getLog4j2();
        logger.getContext().getConfiguration().addLoggerAppender(logger, appender);
    }

    @After
    public void tearDown() {
        appender.stop();
        getLog4j2().removeAppender(appender);
        LoggerFactory.destroy();
    }

    private org.apache.logging.log4j.core.Logger getLog4j2() {
        Logger.Log4j2Logger loggerWrapper = (Logger.Log4j2Logger) LoggerFactory.logger();
        return (org.apache.logging.log4j.core.Logger) loggerWrapper.getLogger();
    }

    @Test
    public void test_info() {
        LoggerFactory.logger().info("test {0} abc", "1");
        LogEvent last = this.appender.getLast();
        Assert.assertEquals("test {} abc", last.getMessage().getFormat());
        Assert.assertArrayEquals(new String[] {"1"}, last.getMessage().getParameters());
        Assert.assertNull(last.getThrown());
        Assert.assertEquals("test 1 abc", last.getMessage().getFormattedMessage());
        Assert.assertEquals(LoggerFactory.logger().mapLevel(Level.INFO), last.getLevel());
    }

    @Test
    public void test_debug() {
        LoggerFactory.logger().debug("test {} abc", "1");
        LogEvent last = this.appender.getLast();
        Assert.assertEquals("test {} abc", last.getMessage().getFormat());
        Assert.assertArrayEquals(new String[] {"1"}, last.getMessage().getParameters());
        Assert.assertNull(last.getThrown());
        Assert.assertEquals("test 1 abc", last.getMessage().getFormattedMessage());
        Assert.assertEquals(LoggerFactory.logger().mapLevel(Level.FINE), last.getLevel());
    }

    @Test
    public void test_trace() {
        LoggerFactory.logger().trace(new RuntimeException(), "test {} abc", "1");
        LogEvent last = this.appender.getLast();
        Assert.assertEquals("test {} abc", last.getMessage().getFormat());
        Assert.assertArrayEquals(new String[] {"1"}, last.getMessage().getParameters());
        Assert.assertTrue(last.getThrown() instanceof RuntimeException);
        Assert.assertEquals("test 1 abc", last.getMessage().getFormattedMessage());
        Assert.assertEquals(LoggerFactory.logger().mapLevel(Level.FINEST), last.getLevel());
    }

    @Test
    public void test_warn() {
        LoggerFactory.logger().warn(new RuntimeException(), "test {0} abc", "1");
        LogEvent last = this.appender.getLast();
        Assert.assertEquals("test {} abc", last.getMessage().getFormat());
        Assert.assertArrayEquals(new String[] {"1"}, last.getMessage().getParameters());
        Assert.assertTrue(last.getThrown() instanceof RuntimeException);
        Assert.assertEquals("test 1 abc", last.getMessage().getFormattedMessage());
        Assert.assertEquals(LoggerFactory.logger().mapLevel(Level.WARNING), last.getLevel());
    }

    @Test
    public void test_error() {
        LoggerFactory.logger().error("test {} abc", "1");
        LogEvent last = this.appender.getLast();
        Assert.assertEquals("test {} abc", last.getMessage().getFormat());
        Assert.assertArrayEquals(new String[] {"1"}, last.getMessage().getParameters());
        Assert.assertNull(last.getThrown());
        Assert.assertEquals("test 1 abc", last.getMessage().getFormattedMessage());
        Assert.assertEquals(LoggerFactory.logger().mapLevel(Level.SEVERE), last.getLevel());
    }

    @Plugin(name = "CustomAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
    public static class CustomAppender extends AbstractAppender {

        @Getter
        private LogEvent last;

        CustomAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
            super(name, filter, layout, false);
        }

        @PluginFactory
        static CustomAppender createAppender(@PluginAttribute("name") String name) {
            if (Strings.isBlank(name)) {
                throw new IllegalArgumentException("Missing name");
            }
            PatternLayout layout = PatternLayout.newBuilder().withPattern("[%t] %-5level %logger{36} - %msg%n")
                                                .withDisableAnsi(true).withCharset(StandardCharsets.UTF_8).build();
            return new CustomAppender(name, null, layout);
        }

        @Override
        public void append(LogEvent event) {
            this.last = event.toImmutable();
        }

    }

}
