package com.zero.oauth.client.client;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.IResponsePropModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.type.HttpMethod;

/**
 * A mixed OAuth client for OAuth 1 and OAuth 2. The different between OAuth1 and OAuth2 is {@code
 * requestTokenUrl}.
 *
 * @param <T> The type of {@code OAuth API} that is maintained by {@code OAuth client}
 * @see OAuthApi
 */
public interface OAuthClient<T extends OAuthApi> {

    /**
     * Create an instance of OAuth v1.0a client.
     *
     * @return OAuth client version 1
     * @see OAuthApi#init(String, String, String, String, String)
     * @see OAuth1Api
     */
    static OAuthClient<OAuth1Api> init(String clientId, String clientSecret, String requestTokenUrl,
                                       String authorizeUrl, String accessTokenUrl) {
        return new OAuth1Client<>(
            (OAuth1Api) OAuthApi.init(clientId, clientSecret, requestTokenUrl, authorizeUrl, accessTokenUrl));
    }

    /**
     * Create an instance of OAuth v2 client.
     *
     * @return OAuth client version 2
     * @see OAuthApi#init(String, String, String, String, GrantType)
     * @see OAuth2Api
     */
    static OAuthClient<OAuth2Api> init(String clientId, String clientSecret, String authorizeUrl,
                                       String accessTokenUrl, GrantType grantType) {
        return new OAuth2Client<>(
            (OAuth2Api) OAuthApi.init(clientId, clientSecret, authorizeUrl, accessTokenUrl, grantType));
    }

    /**
     * An OAuth API is wrapped by OAuth Client.
     *
     * @return An OAuth API is wrapped by OAuth Client
     */
    T getOAuthApi();

    /**
     * Generate the authorize redirect.
     *
     * @param <P> The type of property model
     * @return Response properties that conforms with {@link FlowStep#AUTHORIZE}
     * @see IPropertyModel
     * @see IPropertyStore
     */
    <P extends IResponsePropModel> IPropertyStore<P> generateAuthorizeRedirect();

    /**
     * Exchange {@code access token} with server by the given input is an output of {@link
     * #generateAuthorizeRedirect()}.
     *
     * @param <P> The type of property model
     * @return Response properties that conforms with {@link FlowStep#EXCHANGE_TOKEN}
     * @see IPropertyModel
     * @see IPropertyStore
     */
    <P extends IResponsePropModel> IPropertyStore<P> fetchAccessToken();

    void registerCallback(CallbackHandler callback);

    CallbackHandler getCallback();

    /**
     * Issue request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param method  Http method
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *                particular request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod
     */
    <P extends IResponsePropModel, R extends IPropertyModel> IPropertyStore<P> request(HttpMethod method,
                                                                                       String url,
                                                                                       IPropertyStore<R> payload);

    /**
     * Make {@code GET} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *                particular request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#GET
     */
    default <P extends IResponsePropModel, R extends IPropertyModel> IPropertyStore<P> get(String url,
                                                                                           IPropertyStore<R> payload) {
        return this.request(HttpMethod.GET, url, payload);
    }

    /**
     * Make {@code POST} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *                particular request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#POST
     */
    default <P extends IResponsePropModel, R extends IPropertyModel> IPropertyStore<P> post(String url,
                                                                                            IPropertyStore<R> payload) {
        return this.request(HttpMethod.POST, url, payload);
    }

    /**
     * Make {@code PUT} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *                particular request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#PUT
     */
    default <P extends IResponsePropModel, R extends IPropertyModel> IPropertyStore<P> put(String url,
                                                                                           IPropertyStore<R> payload) {
        return this.request(HttpMethod.PUT, url, payload);
    }

    /**
     * Make {@code PATCH} request to server.
     *
     * @param <P>     The type of response property model
     * @param <R>     The type of request property model
     * @param url     Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *                particular request
     * @param payload Request payload
     * @return Response properties
     * @see HttpMethod#PATCH
     */
    default <P extends IResponsePropModel, R extends IPropertyModel> IPropertyStore<P> patch(String url,
                                                                                             IPropertyStore<R> payload) {
        return this.request(HttpMethod.PATCH, url, payload);
    }

    /**
     * Make {@code DELETE} request to server.
     *
     * @param <P>    The type of response property model
     * @param <R>    The type of request property model
     * @param url    Destination full URL or path(if register specific {@code apiBaseUrl}) for issue
     *               particular request
     * @param header Request header. Any
     * @return Response properties
     * @see HttpMethod#DELETE
     */
    default <P extends IResponsePropModel, R extends IPropertyModel> IPropertyStore<P> delete(String url,
                                                                                              IPropertyStore<R> header) {
        return this.request(HttpMethod.DELETE, url, header);
    }

}
