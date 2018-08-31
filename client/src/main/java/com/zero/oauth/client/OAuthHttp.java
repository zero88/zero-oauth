package com.zero.oauth.client;

import com.zero.oauth.core.properties.GenericResponseStore;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.properties.IResponseProperties;
import com.zero.oauth.core.type.HttpMethod;

public interface OAuthHttp {

    /**
     * Issue request to the {@code Resource server}.
     *
     * @param <R>               The type of request property model
     * @param url               Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *                          particular request
     * @param method            Http method
     * @param requestProperties Request properties, maybe located in {@code HTTP Headers}, {@code URI query} or {@code
     *                          HTTP request body}
     * @return Response properties store
     * @see HttpMethod
     * @see IRequestProperties
     * @see GenericResponseStore
     */
    <R extends IPropertyModel> GenericResponseStore request(String url, HttpMethod method,
                                                            IRequestProperties<R> requestProperties);

    /**
     * Make {@code GET} request to {@code Resource server}.
     *
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param queries Request properties, maybe located in {@code HTTP Headers}, {@code URI query}
     * @return Response properties store
     * @see HttpMethod#GET
     * @see IRequestProperties
     * @see IResponseProperties
     */
    default <R extends IPropertyModel> GenericResponseStore get(String url, IRequestProperties<R> queries) {
        return this.request(url, HttpMethod.GET, queries);
    }

    /**
     * Make {@code POST} request to {@code Resource server}.
     *
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#POST
     * @see IRequestProperties
     * @see IResponseProperties
     */
    default <R extends IPropertyModel> GenericResponseStore post(String url, IRequestProperties<R> payload) {
        return this.request(url, HttpMethod.POST, payload);
    }

    /**
     * Make {@code PUT} request to {@code Resource server}.
     *
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#PUT
     * @see IRequestProperties
     * @see IResponseProperties
     */
    default <R extends IPropertyModel> GenericResponseStore put(String url, IRequestProperties<R> payload) {
        return this.request(url, HttpMethod.PUT, payload);
    }

    /**
     * Make {@code PATCH} request to {@code Resource server}.
     *
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#PATCH
     * @see IRequestProperties
     * @see IResponseProperties
     */
    default <R extends IPropertyModel> GenericResponseStore patch(String url, IRequestProperties<R> payload) {
        return this.request(url, HttpMethod.PATCH, payload);
    }

    /**
     * Make {@code DELETE} request to {@code Resource server}.
     *
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param headers Request header. Any properties that declared in different placements, will be ignore.
     * @return Response properties
     * @see HttpMethod#DELETE
     * @see IRequestProperties
     * @see IResponseProperties
     */
    default <R extends IPropertyModel> GenericResponseStore delete(String url, IRequestProperties<R> headers) {
        return this.request(url, HttpMethod.DELETE, headers);
    }

}
