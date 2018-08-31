package com.zero.oauth.client;

import java.util.concurrent.Future;

import com.zero.oauth.client.http.HttpClientExecutor;
import com.zero.oauth.core.Logger;
import com.zero.oauth.core.properties.GenericResponseStore;

import lombok.Getter;

@Getter
public class OAuth1Client extends AbstractOAuthClient {

    OAuth1Client(OAuth1Api api, ICallbackHandler callback, Logger logger, HttpClientExecutor httpClientExecutor,
                 boolean async) {
        super(api, callback, logger, httpClientExecutor, async);
    }

    @Override
    public Future<GenericResponseStore> authorize() {
        return null;
    }

    @Override
    public Future<GenericResponseStore> exchangeToken() {
        return null;
    }

    @Override
    protected void updateRedirectUrlIntoApi(String callbackUrl) {
        //  this.getApi().getRequestProperties().update(OAuth1RequestProperty.REDIRECT_URI.getName(), callbackUrl);
    }

}
