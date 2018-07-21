package com.zero.oauth.client.type;

import lombok.Getter;

@Getter
public enum HTTPMethod {
    GET(false), POST(true), PUT(true), DELETE(false, true), HEAD(false), OPTIONS(false), TRACE(false), PATCH(true);

    private final boolean requiresBody;
    private final boolean permitBody;

    HTTPMethod(boolean requiresBody) {
        this(requiresBody, requiresBody);
    }

    HTTPMethod(boolean requiresBody, boolean permitBody) {
        if (requiresBody && !permitBody) {
            throw new IllegalArgumentException();
        }
        this.requiresBody = requiresBody;
        this.permitBody = permitBody;
    }

}
