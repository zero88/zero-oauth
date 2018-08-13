package com.zero.oauth.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CallbackHandler implements ICallbackHandler {

    private final String callbackUrl;

    @Override
    public void redirectUserToLoginScreen(String authorizationUrl) {
        System.out.println("Login here: " + authorizationUrl);
    }

}
