package com.zero.oauth.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.zero.oauth.client.http.HttpClientExecutor;
import com.zero.oauth.core.Logger;
import com.zero.oauth.core.properties.GenericResponseStore;

import lombok.Getter;

@Getter
public class OAuth2Client extends AbstractOAuthClient {

    OAuth2Client(OAuth2Api api, ICallbackHandler callback, Logger logger, HttpClientExecutor httpClientExecutor,
                 boolean async) {
        super(api, callback, logger, httpClientExecutor, async);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Future<GenericResponseStore> authorize() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.submit(() -> {
            this.getCallback().redirectUserToLoginScreen(this.getApi().generateAuthorizeRedirect());
            return null;
        });
    }

    @Override
    public Future<GenericResponseStore> exchangeToken() {
        return null;
    }

    @Override
    protected void updateRedirectUrlIntoApi(String callbackUrl) {
        //        this.getApi().getRequestProperties().update(OAuth2RequestProperty.REDIRECT_URI.getName(), callbackUrl);
    }

}
