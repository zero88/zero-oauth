package com.zero.oauth.client.client;

import com.zero.oauth.client.core.oauth2.OAuth2RequestProp;
import com.zero.oauth.client.core.oauth2.OAuth2RequestProperties;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.Strings;

import lombok.Getter;

@Getter
public class OAuth2Api implements OAuthApi {

    private final String clientId;
    private final String clientSecret;
    private final String authorizeUrl;
    private final String accessTokenUrl;
    private final OAuth2RequestProperties defaultPayload;
    private String apiBaseUrl;
    private String refreshTokenUrl;

    /**
     * Constructor to create default OAuth v2 API.
     *
     * @param clientId       Client ID is required value, which you get from client registration.
     * @param clientSecret   Client Secret is required value, which you get from registration.
     * @param authorizeUrl   Endpoint for user authorization of OAuth v2, it is required value.
     * @param accessTokenUrl RAccess Token endpoint for OAuth v2, it is required value.
     * @param grantType      Define client use which OAuth v2 grant type, it is required value.
     * @throws IllegalArgumentException if any missing required value.
     * @see GrantType
     */
    public OAuth2Api(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl,
                     GrantType grantType) {
        this.clientId = Strings.requireNotBlank(clientId);
        this.clientSecret = Strings.requireNotBlank(clientSecret);
        this.authorizeUrl = Strings.requireNotBlank(authorizeUrl);
        this.accessTokenUrl = Strings.requireNotBlank(accessTokenUrl);
        this.defaultPayload = OAuth2RequestProperties.init(grantType);
        this.defaultPayload.update(OAuth2RequestProp.CLIENT_ID.getName(), clientId);
        this.defaultPayload.update(OAuth2RequestProp.CLIENT_SECRET.getName(), clientSecret);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2Api registerApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2Api registerRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
        return this;
    }

}
