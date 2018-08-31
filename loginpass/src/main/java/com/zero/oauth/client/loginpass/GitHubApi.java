package com.zero.oauth.client.loginpass;

import com.zero.oauth.client.OAuth2Api;
import com.zero.oauth.core.type.GrantType;

public class GitHubApi extends OAuth2Api {

    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String API_BASE_URL = "https://api.github.com/";

    public GitHubApi(String clientId, String clientSecret) {
        super(clientId, clientSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL, GrantType.AUTH_CODE);
        this.apiBaseUrl(API_BASE_URL);
        this.getRequestProperties().update("scope", "user:email");
    }

}
