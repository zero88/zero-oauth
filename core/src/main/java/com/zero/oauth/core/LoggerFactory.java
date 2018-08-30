package com.zero.oauth.core;

import java.util.Objects;

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
     * @see Logger.JdkLogger
     */
    public static void initialize() {
        initialize(new Logger.JdkLogger("com.zero.oauth"));
    }

    /**
     * Initialize {@code LoggerFactory} instance with given {@code Logger}.
     *
     * @param logger Logger instance
     * @see Logger
     */
    public static void initialize(Logger logger) {
        if (Objects.nonNull(instance)) {
            instance().getLogger().warn("Logger is already initialized. Skip...");
            return;
        }
        synchronized (LoggerFactory.class) {
            if (instance == null) {
                instance = new LoggerFactory(logger);
            }
        }
    }

}
