package com.zero.oauth.client.client;

public interface IOAuthClient {

    static IOAuthClient init(String clientId, String clientSecret, String requestTokenUrl,
                             String authorizeUrl, String accessTokenUrl, String apiBaseUrl) {
        return new OAuth1Client(clientId, clientSecret, requestTokenUrl, authorizeUrl,
                                accessTokenUrl, apiBaseUrl);
    }

    static IOAuthClient init(String clientId, String clientSecret, String authorizeUrl,
                             String accessTokenUrl, String apiBaseUrl) {
        return new OAuth2Client(clientId, clientSecret, authorizeUrl, accessTokenUrl, apiBaseUrl);
    }

    String getClientId();

    String getClientSecret();

    default String getRequestTokenUrl() {
        return null;
    }

    String getAuthorizeUrl();

    String getAccessTokenUrl();

    String getApiBaseUrl();

}
