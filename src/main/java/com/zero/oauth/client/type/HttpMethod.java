package com.zero.oauth.client.type;

import lombok.Getter;

/**
 * HTTP Request method defines a request's purpose that made by client and sending request to server.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7231#section-4"></a>
 */
@Getter
public enum HttpMethod {
    /**
     * {@code GET} method for transferring resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.1">GET Method</a>
     */
    GET(false),

    /**
     * {@code HEAD} method is identical to {@link #GET} but without response content.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.2">HEAD Method</a>
     */
    HEAD(false),

    /**
     * {@code POST} method for changing resource's state.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.3">POST Method</a>
     */
    POST(true),

    /**
     * {@code PUT} method for creating or replacing resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.4">PUT Method</a>
     */
    PUT(true),

    /**
     * {@code DELETE} method for removing resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.5">DELETE Method</a>
     */
    DELETE(false, true),

    /**
     * {@code PATCH} method for update a set of changes that is partial of resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5789#section-2">PATCH Method</a>
     */
    PATCH(true),

    /**
     * {@code OPTIONS} method for getting information about the communication options available for the target
     * resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.7">OPTIONS Method</a>
     */
    OPTIONS(false),

    /**
     * {@code TRACE} method for requesting a remote, application-level loop-back of the request message.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.8">TRACE Method</a>
     */
    TRACE(false);

    private final boolean requiresBody;
    private final boolean permitBody;

    HttpMethod(boolean requiresBody) {
        this(requiresBody, requiresBody);
    }

    HttpMethod(boolean requiresBody, boolean permitBody) {
        if (requiresBody && !permitBody) {
            throw new IllegalArgumentException();
        }
        this.requiresBody = requiresBody;
        this.permitBody = permitBody;
    }

}
