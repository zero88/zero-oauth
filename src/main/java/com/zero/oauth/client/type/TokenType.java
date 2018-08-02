package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * OAuth Token type in version {@code 2.0}.
 *
 * @see OAuthVersion#V2
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(doNotUseGetters = true, includeFieldNames = false, onlyExplicitlyIncluded = true)
public enum TokenType {

    ACCESS_TOKEN("access_token"), REFRESH_TOKEN("refresh_token");

    @ToString.Include
    private final String type;

}
