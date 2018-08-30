package com.zero.oauth.client;

import com.zero.oauth.core.type.OAuthVersion;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CallbackHandler implements ICallbackHandler {

    @Getter
    private final OAuthVersion version;
    private final String redirectUrl;
    @Getter(value = AccessLevel.PACKAGE)
    private String authorizationUrl;

    @Override
    public String getCallbackUrl() {
        return redirectUrl;
    }

    @Override
    public void redirectUserToLoginScreen(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
        System.out.println("Login here: " + authorizationUrl);
    }

}
