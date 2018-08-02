package com.zero.oauth.client.utils;

import java.util.regex.Pattern;

import com.zero.oauth.client.exceptions.OAuthUrlException;
import com.zero.oauth.client.type.HttpScheme;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * URL Utilities.
 *
 * @see <a href="https://tools.ietf.org/html/rfc1738">[RFC 1738] Uniform Resource Locators</a>
 * @see <a href="https://tools.ietf.org/html/rfc1738#section-5">BNF URL schema</a>
 * @since 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Urls {

    /**
     * Authority syntax.
     *
     * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.2">Authority syntax</a>
     */
    public static final String AUTHORITY_PATTERN =
        "(www\\.)?(([\\w-]+\\.)+[\\w]{2,63}|[\\w-]+)(:[1-9]\\d{1,4})?/?";
    /**
     * Path syntax.
     *
     * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.3">Path syntax</a>
     */
    public static final String PATH_PATTERN =
        "(/([\\w\\$\\-\\.\\+\\!\\*\\'\\(\\)\\,\\;\\:\\@\\&\\=]|(\\%[a-f0-9]{2}))*)*";
    /**
     * URL syntax. In this application case, any {@code query} or {@code fragment} will be not accepted.
     *
     * @see <a href="https://tools.ietf.org/html/rfc3986#section-3">URL syntax</a>
     */
    public static final String URL_PATTERN = HttpScheme.schemeRegex() + AUTHORITY_PATTERN + PATH_PATTERN;

    /**
     * Optimize URL with validation and normalize forward splash ({@code /}).
     *
     * @param base First segment, typically is {@code URL}
     * @param path Second segment, typically is {@code Path}
     * @return String combination of base and path that conforms to {@code URL} syntax
     */
    public static String optimizeUrl(String base, String path) {
        if (Strings.isBlank(base) && Strings.isBlank(path)) {
            throw new IllegalArgumentException("Base URl and path are blank");
        }
        if (validateUrl(path)) {
            return path;
        }
        if (validateUrl(base)) {
            if (Strings.isBlank(path)) {
                return base;
            }
            String normalizePath = normalize(path);
            if (!validatePath(normalizePath)) {
                throw new OAuthUrlException("Invalid path: " + path);
            }
            return normalize(base + normalizePath);
        }
        throw new OAuthUrlException("Invalid url - Base: " + base + " - Path: " + path);
    }

    /**
     * Validate URL.
     *
     * @param url String to validate
     * @return {@code True} if given input is valid URL syntax, otherwise {@code False}
     * @see #URL_PATTERN
     */
    public static boolean validateUrl(String url) {
        return validate(url, URL_PATTERN);
    }

    /**
     * Validate URL Path.
     *
     * @param path String to validate
     * @return {@code True} if given input is valid URL syntax, otherwise {@code False}
     * @see #PATH_PATTERN
     */
    public static boolean validatePath(String path) {
        return validate(path, PATH_PATTERN);
    }

    private static String normalize(String url) {
        return url.replaceAll("/+", "/").replaceFirst("(https?:)/", "$1//");
    }

    private static boolean validate(String s, String pattern) {
        return Strings.isNotBlank(s) &&
               Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(s).matches();
    }

}
