package com.zero.oauth.client;

import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.type.GrantType;
import com.zero.oauth.core.type.OAuthVersion;

/**
 * OAuth API interface. The different between {@code OAuth1} and {@code OAuth2} is {@code requestTokenUrl}
 *
 * @since 1.0.0
 */
public interface OAuthApi {

    /**
     * Factory method to create {@code OAuth v1 API}.
     *
     * @param clientId        Consumer key is required value, which you get from registration.
     * @param clientSecret    Consumer Secrets required value, which you get from registration.
     * @param requestTokenUrl Request Token endpoint for OAuth v1, it is required value.
     * @param authorizeUrl    Endpoint for user authorization of OAuth v1, it is required value.
     * @param accessTokenUrl  Access Token endpoint for OAuth v1, it is required value.
     * @return OAuth v1 API
     * @throws IllegalArgumentException if any missing required value.
     * @see OAuth1Api
     */
    static OAuth1Api initV1(String clientId, String clientSecret, String requestTokenUrl, String authorizeUrl,
                            String accessTokenUrl) {
        return new OAuth1Api(clientId, clientSecret, requestTokenUrl, authorizeUrl, accessTokenUrl);
    }

    /**
     * Factory method to create OAuth v2 API.
     *
     * @param clientId       Client ID is required value, which you get from client registration.
     * @param clientSecret   Client Secret is required value, which you get from registration.
     * @param authorizeUrl   Endpoint for user authorization of OAuth v2, it is required value.
     * @param accessTokenUrl RAccess Token endpoint for OAuth v2, it is required value.
     * @param grantType      Define client use which OAuth v2 grant type, it is required value.
     * @return OAuth v2 API
     * @throws IllegalArgumentException if any missing required value.
     * @see OAuth2Api
     */
    static OAuth2Api initV2(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl,
                            GrantType grantType) {
        return new OAuth2Api(clientId, clientSecret, authorizeUrl, accessTokenUrl, grantType);
    }

    /**
     * OAuth version.
     *
     * @return OAuth API version
     */
    OAuthVersion getVersion();

    /**
     * Client ID which you get from client registration.
     *
     * @return client ID
     */
    String getClientId();

    /**
     * Client Secret which you get from client registration.
     *
     * @return client Secret
     */
    String getClientSecret();

    /**
     * Request Token endpoint for OAuth v1.
     *
     * @return {@code null} if OAuth is version 2
     */
    default String getRequestTokenUrl() {
        return null;
    }

    /**
     * Generate the authorize redirect.
     *
     * @return URL string that to make
     */
    String generateAuthorizeRedirect();

    /**
     * Endpoint for user authorization.
     *
     * @return Authorization endpoint
     */
    String getAuthorizeUrl();

    /**
     * Endpoint for exchange access token.
     *
     * @return Access Token endpoint
     */
    String getAccessTokenUrl();

    /**
     * A base URL endpoint to make requests simple, it is optional value. If not set, when issue any request, a full URL
     * is mandatory.
     *
     * @return {@code null} if it is not defined in OAuth API
     */
    String getApiBaseUrl();

    /**
     * Set API Base URL.
     *
     * @param apiBaseUrl API Base URL
     * @param <T>        Implementation Type of {@link OAuthApi}
     * @return OAuth API
     */
    <T extends OAuthApi> T apiBaseUrl(String apiBaseUrl);

    /**
     * Refresh Token endpoint for OAuth v2.
     *
     * @return {@code null} if OAuth is version 1 or it is not defined in OAuth v2
     */
    default String getRefreshTokenUrl() {
        return null;
    }

    /**
     * Register refresh token endpoint. It is only available on OAuth v2.
     *
     * @param refreshTokenUrl Refresh Token endpoint for OAuth v2
     * @param <T>             Type of OAuth API
     * @return OAuth API instance
     * @throws UnsupportedOperationException if calling from {@code OAuth1Api}
     */
    default <T extends OAuthApi> T refreshTokenUrl(String refreshTokenUrl) {
        throw new UnsupportedOperationException("Not support refresh token flow.");
    }

    /**
     * OAuth Request properties, it can be used to create HTTP header, HTTP Query or HTTP Body in request.
     * <ul>
     * <li>Builtin standard properties in OAuth flow, that helps making request to
     * Authorization server then achieving credentials token to access Resource server.</li>
     * <li>Any custom properties</li>
     * </ul>
     *
     * @return OAuth request properties
     */
    IRequestProperties getRequestProperties();

}
