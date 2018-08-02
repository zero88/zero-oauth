package com.zero.oauth.client.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Strings Utilities.
 *
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Strings {

    /**
     * Check given text is blank or not.
     *
     * @param text the text to check for blank
     * @return {@code True} if blank, else otherwise
     */
    public static boolean isBlank(String text) {
        return text == null || "".equals(text.trim());
    }

    /**
     * Check given text is not blank or not. The reversation of {@link #isBlank(String)}
     *
     * @param text the text to check for blank
     * @return {@code True} if not blank, else otherwise
     */
    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    /**
     * Checks that the specified string reference is not {@code blank}. This method is designed primarily for
     * doing parameter validation in methods and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Strings.requireNotBlank(bar);
     * }
     * </pre></blockquote>
     *
     * @param text the text to check for blank
     * @return Trimmed {@code text} if not {@code blank}
     * @throws IllegalArgumentException if {@code obj} is {@code blank}
     */
    public static String requireNotBlank(String text) {
        return requireNotBlank(text, "");
    }

    /**
     * Checks that the specified string reference is not {@code blank}. This method is designed primarily for
     * doing parameter validation in methods and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Strings.requireNotBlank(bar, "String cannot blank");
     * }
     * </pre></blockquote>
     *
     * @param text    the text to check for blank
     * @param message the error message will be included in exception
     * @return Trimmed {@code text} if not {@code blank}
     * @throws IllegalArgumentException if {@code obj} is {@code blank}
     */
    public static String requireNotBlank(String text, String message) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
        return text.trim();
    }

}
