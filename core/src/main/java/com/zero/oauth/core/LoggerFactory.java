package com.zero.oauth.core;

import java.util.Objects;

import com.zero.oauth.core.utils.Reflections;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Logger Factory
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoggerFactory {

    private static volatile LoggerFactory instance;
    private final Logger logger;

    public static LoggerFactory instance() {
        if (Objects.isNull(instance)) {
            throw new IllegalStateException("Must be initialize first");
        }
        return instance;
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

}
