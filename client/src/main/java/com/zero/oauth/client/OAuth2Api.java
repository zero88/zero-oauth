package com.zero.oauth.client;

import com.zero.oauth.core.converter.HttpQueryConverter;
import com.zero.oauth.core.oauth2.OAuth2RequestProperties;
import com.zero.oauth.core.oauth2.OAuth2RequestProperty;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.GrantType;
import com.zero.oauth.core.type.OAuthVersion;
import com.zero.oauth.core.utils.Strings;
import com.zero.oauth.core.utils.Urls;

import lombok.Getter;

@Getter
public class OAuth2Api extends AbstractOAuthApi {

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
        super(Strings.requireNotBlank(authorizeUrl), Strings.requireNotBlank(accessTokenUrl),
              OAuth2RequestProperties.init(grantType));
        this.updateClientInfo(clientId, clientSecret);
    }

    @Override
    protected String getClientIdName() {
        return OAuth2RequestProperty.CLIENT_ID.getName();
    }

    @Override
    protected String getClientSecretName() {
        return OAuth2RequestProperty.CLIENT_SECRET.getName();
    }

    @Override
    public final OAuthVersion getVersion() {
        return OAuthVersion.V2;
    }

    @Override
    public String generateAuthorizeRedirect() {
        return Urls.buildURL(this.getAuthorizeUrl(),
                             new HttpQueryConverter().serialize(this.getRequestProperties(), FlowStep.AUTHORIZE));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final OAuth2Api refreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
        return this;
    }

}
