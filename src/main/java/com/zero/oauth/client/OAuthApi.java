package com.zero.oauth.client;

import com.zero.oauth.client.core.properties.OAuthProperties;
import com.zero.oauth.client.type.GrantType;

/**
 * OAuth API interface.
 *
 * @since 1.0.0
 */
public interface OAuthApi {

    /**
     * Factory method to create OAuth v1 API.
     *
     * @return OAuth v1 API
     * @see OAuth1Api#init(String, String, String, String, String)
     */
    static OAuthApi init(String clientId, String clientSecret, String requestTokenUrl, String authorizeUrl,
                         String accessTokenUrl) {
        return new OAuth1Api(clientId, clientSecret, requestTokenUrl, authorizeUrl, accessTokenUrl);
    }

    /**
     * Factory method to create OAuth v2 API.
     *
     * @return OAuth v2 API
     * @see OAuth2Api#init(String, String, String, String, GrantType)
     */
    static OAuthApi init(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl,
                         GrantType grantType) {
        return new OAuth2Api(clientId, clientSecret, authorizeUrl, accessTokenUrl, grantType);
    }

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
     * A base URL endpoint to make requests simple, it is optional value. If not set, when issue any request,
     * a full URL is mandatory.
     *
     * @return {@code null} if it is not defined in OAuth API
     */
    String getApiBaseUrl();

    <T extends OAuthApi> T registerApiBaseUrl(String apiBaseUrl);

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
    default <T extends OAuth2Api> T registerRefreshTokenUrl(String refreshTokenUrl) {
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
    OAuthProperties getRequestProperties();

}
