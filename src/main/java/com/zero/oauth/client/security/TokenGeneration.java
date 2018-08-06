package com.zero.oauth.client.security;

import java.util.function.Function;

import com.zero.oauth.client.utils.Strings;

/**
 * Random token generation function.
 * <p>
 * If a given input in {@link #apply(String)} is blank, it will trigger to random new token.
 */
public final class TokenGeneration implements Function<String, String> {

    @Override
    public String apply(String token) {
        if (Strings.isNotBlank(token)) {
            return token;
        }
        return ServiceRegistry.getSecurityService().randomToken();
    }

}
