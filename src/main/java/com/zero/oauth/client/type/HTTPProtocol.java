package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(doNotUseGetters = true, includeFieldNames = false, onlyExplicitlyIncluded = true)
public enum HTTPProtocol {

    HTTP("http"), HTTPS("https");

    @ToString.Include
    private final String schema;
}
