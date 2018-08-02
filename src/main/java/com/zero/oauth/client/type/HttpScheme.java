package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * HTTP scheme.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7230#section-8.2">URI Scheme</a>
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum HttpScheme {

    HTTP("http"), HTTPS("https");

    private final String scheme;

    public static String schemeRegex() {
        return "(https?://)";
    }

    @Override
    public String toString() {
        return this.scheme;
    }
}
