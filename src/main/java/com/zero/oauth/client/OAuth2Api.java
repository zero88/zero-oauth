package com.zero.oauth.client;

import com.zero.oauth.client.core.oauth2.OAuth2RequestProperties;
import com.zero.oauth.client.core.oauth2.OAuth2RequestProperty;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.IResponseProperty;
import com.zero.oauth.client.core.properties.converter.HttpQueryConverter;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.Strings;
import com.zero.oauth.client.utils.Urls;

import lombok.Getter;

@Getter
public class OAuth2Api implements OAuthApi {

    private final String clientId;
    private final String clientSecret;
    private final String authorizeUrl;
    private final String accessTokenUrl;
    private final OAuth2RequestProperties requestProperties;
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
        this.requestProperties = OAuth2RequestProperties.init(grantType);
        this.requestProperties.update(OAuth2RequestProperty.CLIENT_ID.getName(), clientId);
        this.requestProperties.update(OAuth2RequestProperty.CLIENT_SECRET.getName(), clientSecret);
    }

    @Override
    public String generateAuthorizeRedirect() {
        return Urls.buildURL(this.getAuthorizeUrl(),
                             new HttpQueryConverter<>(this.requestProperties).serialize(FlowStep.AUTHORIZE), null);
    }

    @Override
    public <P extends IResponseProperty> IPropertyStore<P> fetchAccessToken() {
        return null;
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
