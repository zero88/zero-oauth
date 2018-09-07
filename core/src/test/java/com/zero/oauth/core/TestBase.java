package com.zero.oauth.core;

import org.junit.BeforeClass;

public class TestBase {

    @BeforeClass
    public static void setUpClass() {
        LoggerFactory.initialize("com.zero.oauth.test");
    }

    public interface ThrowableConsumer<T> {

        default void apply(T o, Class<?> clazz, String methodName) throws Throwable {

        }

    }

}
