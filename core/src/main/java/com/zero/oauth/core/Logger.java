package com.zero.oauth.core;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINEST;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.OFF;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

import java.text.MessageFormat;
import java.util.logging.Level;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * This is a proxy to plug the {@code application logger} into {@code OAuth} library.
 */
public interface Logger {

    default String getName() {
        return "com.zero.oauth";
    }

    void log(Level level, String msg);

    void log(Level level, String msgPattern, Object... params);

    void log(Level level, Throwable throwable, String msg);

    void log(Level level, Throwable throwable, String msgPattern, Object... params);

    default void trace(String msg) {
        log(FINEST, msg);
    }

    default void trace(String msgPattern, Object... params) {
        log(FINEST, msgPattern, params);
    }

    default void trace(Throwable throwable, String msg) {
        log(FINEST, throwable, msg);
    }

    default void trace(Throwable throwable, String msgPattern, Object... params) {
        log(FINEST, throwable, msgPattern, params);
    }

    default void debug(String msg) {
        log(FINE, msg);
    }

    default void debug(String msgPattern, Object... params) {
        log(FINE, msgPattern, params);
    }

    default void debug(Throwable throwable, String msg) {
        log(FINE, throwable, msg);
    }

    default void debug(Throwable throwable, String msgPattern, Object... params) {
        log(FINE, throwable, msgPattern, params);
    }

    default void info(String msg) {
        log(INFO, msg);
    }

    default void info(String msgPattern, Object... params) {
        log(INFO, msgPattern, params);
    }

    default void info(Throwable throwable, String msg) {
        log(INFO, throwable, msg);
    }

    default void info(Throwable throwable, String msgPattern, Object... params) {
        log(INFO, throwable, msgPattern, params);
    }

    default void warn(String msg) {
        log(WARNING, msg);
    }

    default void warn(String msgPattern, Object... params) {
        log(WARNING, msgPattern, params);
    }

    default void warn(Throwable throwable, String msg) {
        log(WARNING, throwable, msg);
    }

    default void warn(Throwable throwable, String msgPattern, Object... params) {
        log(WARNING, throwable, msgPattern, params);
    }

    default void error(String msg) {
        log(SEVERE, msg);
    }

    default void error(String msgPattern, Object... params) {
        log(SEVERE, msgPattern, params);
    }

    default void error(Throwable throwable, String msg) {
        log(SEVERE, throwable, msg);
    }

    default void error(Throwable throwable, String msgPattern, Object... params) {
        log(SEVERE, throwable, msgPattern, params);
    }

    <L> L mapLevel(Level level);

    class JdkLogger implements Logger {

        @Getter(value = AccessLevel.PACKAGE)
        private final java.util.logging.Logger logger;

        JdkLogger(String name) {
            logger = java.util.logging.Logger.getLogger(name);
        }

        @Override
        public String getName() {
            return logger.getName();
        }

        @Override
        public void log(Level level, String msg) {
            logger.log(level, msg);
        }

        @Override
        public void log(Level level, String msgPattern, Object... params) {
            logger.log(level, msgPattern, params);
        }

        @Override
        public void log(Level level, Throwable throwable, String msg) {
            logger.log(level, msg, throwable);
        }

        @Override
        public void log(Level level, Throwable throwable, String msgPattern, Object... params) {
            logger.log(level, MessageFormat.format(msgPattern, params), throwable);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Level mapLevel(Level level) {
            return level;
        }

    }


    class Log4j2Logger implements Logger {

        @Getter(value = AccessLevel.PACKAGE)
        private final org.apache.logging.log4j.Logger logger;

        Log4j2Logger(String name) {
            logger = org.apache.logging.log4j.LogManager.getLogger(name);
        }

        private String normalize(String msgPattern) {
            return msgPattern.replaceAll("\\{\\d+\\}", "{}");
        }

        @Override
        public String getName() {
            return logger.getName();
        }

        @Override
        public void log(Level level, String msg) {
            logger.log(this.mapLevel(level), msg);
        }

        @Override
        public void log(Level level, String msgPattern, Object... params) {
            logger.log(this.mapLevel(level), normalize(msgPattern), params);
        }

        @Override
        public void log(Level level, Throwable throwable, String msg) {
            logger.log(this.mapLevel(level), msg, throwable);
        }

        @Override
        public void log(Level level, Throwable throwable, String msgPattern, Object... params) {
            logger.log(this.mapLevel(level),
                       new org.apache.logging.log4j.message.ParameterizedMessageFactory().newMessage(normalize(msgPattern), params), throwable);
        }

        @SuppressWarnings("unchecked")
        @Override
        public org.apache.logging.log4j.Level mapLevel(Level level) {
            //@formatter:off
            int value = level.intValue();
            if (value < FINE.intValue()) return org.apache.logging.log4j.Level.TRACE;
            if (value < INFO.intValue()) return org.apache.logging.log4j.Level.DEBUG;
            if (value < WARNING.intValue()) return org.apache.logging.log4j.Level.INFO;
            if (value < SEVERE.intValue()) return org.apache.logging.log4j.Level.WARN;
            if (value < OFF.intValue()) return org.apache.logging.log4j.Level.ERROR;
            return org.apache.logging.log4j.Level.toLevel(level.getName(), org.apache.logging.log4j.Level.ALL);
            //@formatter:on
        }

    }


    class LogbackLogger implements Logger {

        @Getter(value = AccessLevel.PACKAGE)
        private final org.slf4j.Logger logger;

        LogbackLogger(String name) {
            logger = org.slf4j.LoggerFactory.getLogger(name);
        }

        @Override
        public String getName() {
            return logger.getName();
        }

        @Override
        public void log(Level level, String msg) {
            this.log(level, null, msg, new Object[] {});
        }

        @Override
        public void log(Level level, String msgPattern, Object... params) {
            this.log(level, null, msgPattern, params);
        }

        @Override
        public void log(Level level, Throwable throwable, String msg) {
            this.log(level, throwable, msg, new Object[] {});
        }

        @Override
        public void log(Level level, Throwable throwable, String msgPattern, Object... params) {
            String msg = MessageFormat.format(msgPattern, params);
            int value = level.intValue();
            if (value < FINE.intValue()) {
                this.logger.trace(msg, throwable);
                return;
            }
            if (value < INFO.intValue()) {
                this.logger.debug(msg, throwable);
                return;
            }
            if (value < WARNING.intValue()) {
                this.logger.info(msg, throwable);
                return;
            }
            if (value < SEVERE.intValue()) {
                this.logger.warn(msg, throwable);
                return;
            }
            if (value < OFF.intValue()) {
                this.logger.error(msg, throwable);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public Level mapLevel(Level level) {
            return level;
        }

    }

}
