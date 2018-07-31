package com.zero.oauth.client.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OAuth1Client implements IOAuthClient {

    private final String clientId;
    private final String clientSecret;
    private final String requestTokenUrl;
    private final String authorizeUrl;
    private final String accessTokenUrl;
    private final String apiBaseUrl;

}
