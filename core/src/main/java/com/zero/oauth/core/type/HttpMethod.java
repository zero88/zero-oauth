package com.zero.oauth.core.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * HTTP Request method defines a request's purpose that made by client and sending request to server.
 *
 * @see <a href="https://tools.ietf.org/html/rfc7231#section-4">[REF7231] Section 4</a>
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Getter
public enum HttpMethod {
    /**
     * {@code GET} method for transferring resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.1">GET Method</a>
     */
    GET(Arrays.asList(HttpPlacement.HEADER, HttpPlacement.URI_QUERY)),

    /**
     * {@code HEAD} method is identical to {@link #GET} but without response content.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.2">HEAD Method</a>
     */
    HEAD(Arrays.asList(HttpPlacement.HEADER, HttpPlacement.URI_QUERY)),

    /**
     * {@code POST} method for changing resource's state.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.3">POST Method</a>
     */
    POST(Arrays.asList(HttpPlacement.HEADER, HttpPlacement.URI_QUERY, HttpPlacement.BODY)),

    /**
     * {@code PUT} method for creating or replacing resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.4">PUT Method</a>
     */
    PUT(Arrays.asList(HttpPlacement.HEADER, HttpPlacement.URI_QUERY, HttpPlacement.BODY)),

    /**
     * {@code DELETE} method for removing resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.5">DELETE Method</a>
     */
    DELETE(Collections.singletonList(HttpPlacement.HEADER)),

    /**
     * {@code PATCH} method for update a set of changes that is partial of resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5789#section-2">PATCH Method</a>
     */
    PATCH(Arrays.asList(HttpPlacement.HEADER, HttpPlacement.URI_QUERY, HttpPlacement.BODY)),

    /**
     * {@code OPTIONS} method for getting information about the communication options available for the target
     * resource.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.7">OPTIONS Method</a>
     */
    OPTIONS(Collections.singletonList(HttpPlacement.HEADER)),

    /**
     * {@code TRACE} method for requesting a remote, application-level loop-back of the request message.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-4.3.8">TRACE Method</a>
     */
    TRACE(Collections.singletonList(HttpPlacement.HEADER));

    private final List<HttpPlacement> availablePlaces;

}
