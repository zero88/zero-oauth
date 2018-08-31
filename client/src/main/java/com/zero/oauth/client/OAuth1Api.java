package com.zero.oauth.client;

import com.zero.oauth.core.oauth1.OAuth1RequestProperties;
import com.zero.oauth.core.oauth1.OAuth1RequestProperty;
import com.zero.oauth.core.type.OAuthVersion;
import com.zero.oauth.core.utils.Strings;

import lombok.Getter;

/**
 * That holds the required information to interact with OAuth version 1.
 */
@Getter
public class OAuth1Api extends AbstractOAuthApi {

    private final String requestTokenUrl;

    /**
     * Constructor to create default OAuth v1 API.
     *
     * @param clientId        Consumer key is required value, which you get from registration.
     * @param clientSecret    Consumer Secrets required value, which you get from registration.
     * @param requestTokenUrl Request Token endpoint for OAuth v1, it is required value.
     * @param authorizeUrl    Endpoint for user authorization of OAuth v1, it is required value.
     * @param accessTokenUrl  Access Token endpoint for OAuth v1, it is required value.
     * @throws IllegalArgumentException if any missing required value.
     */
    public OAuth1Api(String clientId, String clientSecret, String requestTokenUrl, String authorizeUrl,
                     String accessTokenUrl) {
        super(Strings.requireNotBlank(authorizeUrl), Strings.requireNotBlank(accessTokenUrl),
              new OAuth1RequestProperties());
        this.requestTokenUrl = Strings.requireNotBlank(requestTokenUrl);
        this.updateClientInfo(clientId, clientSecret);
    }

    @Override
    protected String getClientIdName() {
        return OAuth1RequestProperty.CONSUMER_KEY.getName();
    }

    @Override
    protected String getClientSecretName() {
        return OAuth1RequestProperty.CONSUMER_SECRET.getName();
    }

    @Override
    public OAuthVersion getVersion() {
        return OAuthVersion.V1;
    }

    @Override
    public String generateAuthorizeRedirect() {
        return null;
    }

}
