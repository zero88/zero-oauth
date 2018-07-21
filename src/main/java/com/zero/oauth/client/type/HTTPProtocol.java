package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public enum HTTPProtocol {

    HTTP("http"), HTTPS("https");

    private final String schema;
}
