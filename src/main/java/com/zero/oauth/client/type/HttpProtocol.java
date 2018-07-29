package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum HttpProtocol {

    HTTP("http"), HTTPS("https");

    private final String schema;

    @Override
    public String toString() {
        return this.schema;
    }
}
