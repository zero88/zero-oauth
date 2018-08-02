package com.zero.oauth.client.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import com.zero.oauth.client.exceptions.OAuthException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * OAuth Encode/Decode Utilities for URI Query or HTTP Header.
 *
 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2">Character encoding</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OAuthEncoder {

    private static final Map<String, String> ENCODING_RULES;

    static {
        final Map<String, String> rules = new HashMap<>();
        rules.put("*", "%2A");
        rules.put("+", "%20");
        rules.put("%7E", "~");
        ENCODING_RULES = Collections.unmodifiableMap(rules);
    }

    /**
     * Encode plain text in {@code UTF-8} encoding and follow standardization format.
     *
     * @param plain text to encode
     * @return Encoded value
     * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.6">Percent encoding</a>
     * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.2">Reserved Characters</a>
     * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.3">Unreserved Characters</a>
     * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.4">When to Encode or Decode</a>
     */
    public static String encode(String plain) {
        Objects.requireNonNull(plain, "Cannot encode null object");
        String encoded;
        try {
            encoded = URLEncoder.encode(plain, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException uee) {
            throw new OAuthException("Charset not found while encoding string: " + StandardCharsets.UTF_8,
                                     uee);
        }
        for (Map.Entry<String, String> rule : ENCODING_RULES.entrySet()) {
            encoded = applyRule(encoded, rule.getKey(), rule.getValue());
        }
        return encoded;
    }

    /**
     * Decode encoded text for human readable.
     *
     * @param encoded Encoded value to decode
     * @return Plain text
     */
    public static String decode(String encoded) {
        Objects.requireNonNull(encoded, "Cannot decode null object");
        try {
            return URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException uee) {
            throw new OAuthException("Charset not found while decoding string: " + StandardCharsets.UTF_8,
                                     uee);
        }
    }

    private static String applyRule(String encoded, String toReplace, String replacement) {
        return encoded.replaceAll(Pattern.quote(toReplace), replacement);
    }

}
