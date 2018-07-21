package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * OAuth Token type in version <code>2.0</code>.
 *
 * @see OAuthVersion#V2
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public enum TokenType {

    ACCESS_TOKEN("access_token"), REFRESH_TOKEN("refresh_token");

    private final String type;
}
