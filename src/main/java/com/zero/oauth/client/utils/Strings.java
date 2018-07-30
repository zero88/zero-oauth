package com.zero.oauth.client.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Strings {

    public static boolean isBlank(String text) {
        return text == null || "".equals(text.trim());
    }

    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    public static String requireNotBlank(String text) {
        return requireNotBlank(text, "");
    }

    public static String requireNotBlank(String text, String message) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
        return text.trim();
    }
}
