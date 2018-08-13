package com.zero.oauth.client;

import java.util.Objects;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.IResponseProperty;
import com.zero.oauth.client.type.HttpMethod;

import lombok.Getter;

/**
 * A mixed OAuth client that carry out {@code OAuth v1 API} and {@code OAuth v2 API}, helps to execute HTTP request to
 * server then receiving and processing HTTP response.
 *
 * @param <T> The type of {@code OAuth API} that is maintained by {@code OAuth client}
 * @see OAuthApi
 * @since 1.0.0
 */
@Getter
public abstract class OAuthClient<T extends OAuthApi> {

    /**
     * An OAuth API is wrapped by OAuth Client.
     */
    private T api;
    private ICallbackHandler callback;

    public OAuthClient registerApi(T api) {
        this.api = Objects.requireNonNull(api);
        return this;
    }

    public OAuthClient registerCallbackUrl(String callbackUrl) {
        this.callback = new CallbackHandler(callbackUrl);
        return this;
    }

    public OAuthClient registerCallback(ICallbackHandler callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Issue request to server.
     *
     * @param <P>      The type of response property model
     * @param <R>      The type of request property model
     * @param method   Http method
     * @param url      Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                 request
     * @param reqProps Request properties, maybe located in {@code HTTP Headers}, {@code URI query} or {@code HTTP
     *                 request body}
     * @return Response properties
     * @see HttpMethod
     */
    public abstract <P extends IResponseProperty, R extends IPropertyModel> IPropertyStore<P> request(HttpMethod method,
                                                                                                      String url,
                                                                                                      IPropertyStore<R> reqProps);

    /**
     * Make {@code GET} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param queries Request properties, maybe located in {@code HTTP Headers}, {@code URI query}
     * @return Response properties
     * @see HttpMethod#GET
     */
    public <P extends IResponseProperty, R extends IPropertyModel> IPropertyStore<P> get(String url,
                                                                                         IPropertyStore<R> queries) {
        return this.request(HttpMethod.GET, url, queries);
    }

    /**
     * Make {@code POST} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#POST
     */
    public <P extends IResponseProperty, R extends IPropertyModel> IPropertyStore<P> post(String url,
                                                                                          IPropertyStore<R> payload) {
        return this.request(HttpMethod.POST, url, payload);
    }

    /**
     * Make {@code PUT} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#PUT
     */
    public <P extends IResponseProperty, R extends IPropertyModel> IPropertyStore<P> put(String url,
                                                                                         IPropertyStore<R> payload) {
        return this.request(HttpMethod.PUT, url, payload);
    }

    /**
     * Make {@code PATCH} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#PATCH
     */
    public <P extends IResponseProperty, R extends IPropertyModel> IPropertyStore<P> patch(String url,
                                                                                           IPropertyStore<R> payload) {
        return this.request(HttpMethod.PATCH, url, payload);
    }

    /**
     * Make {@code DELETE} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue particular
     *                request
     * @param headers Request header. Any properties that declared in different placements, will be ignore.
     * @return Response properties
     * @see HttpMethod#DELETE
     */
    public <P extends IResponseProperty, R extends IPropertyModel> IPropertyStore<P> delete(String url,
                                                                                            IPropertyStore<R> headers) {
        return this.request(HttpMethod.DELETE, url, headers);
    }

}
