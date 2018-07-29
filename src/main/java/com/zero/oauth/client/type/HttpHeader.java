package com.zero.oauth.client.type;

import java.util.Arrays;
import java.util.Optional;

public enum HttpHeader {

    /**
     * Authorization HTTP header.
     */
    AUTHORIZATION("Authorization"),

    /**
     * WWW-Authenticate HTTP header.
     */
    WWW_AUTHENTICATE("WWW-Authenticate"),

    /**
     * Date HTTP header.
     */
    DATE("Date"),

    /**
     * Host HTTP header.
     */
    HOST("Host"),

    /**
     * Location HTTP header.
     */
    LOCATION("Location"),

    /**
     * Origin HTTP header.
     */
    ORIGIN("Origin"),

    /**
     * x-correlation-id HTTP header.
     */
    X_CORRELATION_ID("x-correlation-id");

    private final String name;

    HttpHeader(final String name) {
        this.name = name;
    }

    /**
     * Returns a {@code HttpHeader} from a given string representation.
     *
     * @param name
     *        the string representation.
     * @return the HttpHeader.
     */
    public static Optional<HttpHeader> fromName(final String name) {
        return Arrays.stream(values()).filter(header -> name.equals(header.toString())).findFirst();
    }

    @Override
    public String toString() {
        return name;
    }
}
