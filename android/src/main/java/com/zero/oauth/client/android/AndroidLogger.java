package com.zero.oauth.client.android;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.OFF;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

import java.util.logging.Level;

import com.zero.oauth.core.Logger;

public class AndroidLogger implements Logger {

    static final boolean ANDROID_LOG_AVAILABLE;

    static {
        boolean android = false;
        try {
            android = Class.forName("android.util.Log") != null;
        } catch (ClassNotFoundException e) {
            // OK
        }
        ANDROID_LOG_AVAILABLE = android;
    }

    private final String name;

    boolean isAndroidLogAvailable() {
        return ANDROID_LOG_AVAILABLE;
    }

    public void log(Level level, String msg) {
        if (level != OFF) {
            android.util.Log.println(mapLevel(level), this.getName(), msg);
        }
    }

    @Override
    public void log(Level level, String msgPattern, Object... params) {
        if (level != OFF) {
            android.util.Log.println(mapLevel(level), this.getName(), msg);
        }
    }

    @Override
    public void log(Level level, Throwable throwable, String msg) {
        if (level != OFF) {
            android.util.Log.println(mapLevel(level), this.getName(),
                                     msg + "\n" + android.util.Log.getStackTraceString(throwable));
        }
    }

    @Override
    public void log(Level level, Throwable throwable, String msgPattern, Object... params) {
        if (level != OFF) {
            android.util.Log.println(mapLevel(level), this.getName(), msg);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer mapLevel(Level level) {
        int value = level.intValue();
        if (value < FINE.intValue()) {
            return android.util.Log.VERBOSE;
        }
        if (value < INFO.intValue()) {
            return android.util.Log.DEBUG;
        }
        if (value < WARNING.intValue()) {
            return android.util.Log.INFO;
        }
        if (value < SEVERE.intValue()) {
            return android.util.Log.WARN;
        }
        if (value < OFF.intValue()) {
            return android.util.Log.ERROR;
        }
        return android.util.Log.INFO;
    }

}
