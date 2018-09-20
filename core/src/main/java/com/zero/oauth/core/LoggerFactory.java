package com.zero.oauth.core;

import java.util.Objects;

import com.zero.oauth.core.utils.Reflections;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Logger Factory.
 *
 * @since 1.0.0
 */
@Getter(value = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggerFactory {

    private static volatile LoggerFactory instance;
    private final Logger logger;

    /**
     * Get singleton {@code LoggerFactory}
     *
     * @return LoggerFactory
     * @throws IllegalStateException if not yet initialize factory
     * @see #initialize(Logger)
     * @see #initialize(String, Class)
     * @see #initialize(Logger)
     */
    public static LoggerFactory instance() {
        if (Objects.isNull(instance)) {
            throw new IllegalStateException("Must be initialize first");
        }
        return instance;
    }

    /**
     * Shortcut method to get logger instance that held by {@code LoggerFactory} singleton object.
     *
     * @return logger
     * @see #instance()
     */
    public static Logger logger() {
        return LoggerFactory.instance().getLogger();
    }

    /**
     * Initialize {@code LoggerFactory} instance with {@code JDK logger}.
     *
     * @param loggerName Logger name.
     * @see Logger.JdkLogger
     */
    public static void initialize(String loggerName) {
        initialize(loggerName, Logger.JdkLogger.class);
    }

    /**
     * Initialize {@code LoggerFactory} instance with {@code JDK logger}.
     *
     * @param loggerName  Logger name
     * @param loggerClass Logger class
     * @param <L>         Type of logger class
     * @see Logger
     */
    public static <L extends Logger> void initialize(String loggerName, Class<L> loggerClass) {
        initialize(Reflections.initInstance(loggerClass, new Class[] {String.class}, new Object[] {loggerName}));
    }

    /**
     * Initialize {@code LoggerFactory} instance with given {@code Logger}.
     *
     * @param logger Logger instance
     * @see Logger
     */
    public static void initialize(Logger logger) {
        Objects.requireNonNull(logger, "Logger cannot be null");
        if (Objects.nonNull(instance)) {
            instance().getLogger().trace("Logger is already initialized. Skip...");
            return;
        }
        synchronized (LoggerFactory.class) {
            if (instance == null) {
                instance = new LoggerFactory(logger);
            }
        }
    }

    static synchronized void destroy() {
        instance = null;
    }

}
