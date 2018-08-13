package com.zero.oauth.client;

import com.zero.oauth.client.core.oauth1.OAuth1RequestProperties;
import com.zero.oauth.client.core.oauth1.OAuth1RequestProperty;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.IResponseProperty;
import com.zero.oauth.client.utils.Strings;

import lombok.Getter;

@Getter
public class OAuth1Api implements OAuthApi {

    private final String clientId;
    private final String clientSecret;
    private final String requestTokenUrl;
    private final String authorizeUrl;
    private final String accessTokenUrl;
    private final OAuth1RequestProperties requestProperties;
    private String apiBaseUrl;

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
        this.clientId = Strings.requireNotBlank(clientId);
        this.clientSecret = Strings.requireNotBlank(clientSecret);
        this.requestTokenUrl = Strings.requireNotBlank(requestTokenUrl);
        this.authorizeUrl = Strings.requireNotBlank(authorizeUrl);
        this.accessTokenUrl = Strings.requireNotBlank(accessTokenUrl);
        this.requestProperties = new OAuth1RequestProperties();
        this.requestProperties.update(OAuth1RequestProperty.CONSUMER_KEY.getName(), clientId);
        this.requestProperties.update(OAuth1RequestProperty.CONSUMER_SECRET.getName(), clientSecret);
    }

    @Override
    public String generateAuthorizeRedirect() {
        return null;
    }

    @Override
    public <P extends IResponseProperty> IPropertyStore<P> fetchAccessToken() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth1Api registerApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        return this;
    }

}
