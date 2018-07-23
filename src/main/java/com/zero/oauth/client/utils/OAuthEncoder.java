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

    public static String encode(String plain) {
        Objects.requireNonNull(plain, "Cannot encode null object");
        String encoded;
        try {
            encoded = URLEncoder.encode(plain, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException uee) {
            throw new OAuthException("Charset not found while encoding string: " + StandardCharsets.UTF_8, uee);
        }
        for (Map.Entry<String, String> rule : ENCODING_RULES.entrySet()) {
            encoded = applyRule(encoded, rule.getKey(), rule.getValue());
        }
        return encoded;
    }

    private static String applyRule(String encoded, String toReplace, String replacement) {
        return encoded.replaceAll(Pattern.quote(toReplace), replacement);
    }

    public static String decode(String encoded) {
        Objects.requireNonNull(encoded, "Cannot decode null object");
        try {
            return URLDecoder.decode(encoded, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException uee) {
            throw new OAuthException("Charset not found while decoding string: " + StandardCharsets.UTF_8, uee);
        }
    }
}
