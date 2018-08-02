package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Defines OAuth versions.
 *
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(doNotUseGetters = true, includeFieldNames = false, onlyExplicitlyIncluded = true)
public enum OAuthVersion {

    /**
     * OAuth version 1.0a.
     * <p>
     * Specifications:
     * </p>
     * <ul>
     * <li><a href="http://tools.ietf.org/html/rfc5849">[RFC 5849]</a></li>
     * <li><a href="https://oauth.net/core/1.0/">OAuth 1.0</a></li>
     * <li><a href="https://oauth.net/core/1.0a/">OAuth 1.0a</a></li>
     * </ul>
     */
    V1("1.0a"),
    /**
     * OAuth version 2.0 <a href="https://tools.ietf.org/html/rfc6749">[RFC 6749]</a>
     * <p>
     * Specifications:
     * </p>
     * <ul>
     * <li><a href="https://tools.ietf.org/html/rfc6749">[RFC6749]</a></li>
     * <li><a href="https://oauth.net/core/2/">OAuth 2.0</a></li>
     * </ul>
     */
    V2("2.0"),
    /**
     * Just for developing.
     */
    ALL("");

    @ToString.Include
    private final String version;

    public boolean isV1() {
        return this == V1 || this == ALL;
    }

    public boolean isV2() {
        return this == V2 || this == ALL;
    }

    public boolean isEqual(OAuthVersion version) {
        return this.isV1() && version.isV1() || this.isV2() && version.isV2();
    }
}
