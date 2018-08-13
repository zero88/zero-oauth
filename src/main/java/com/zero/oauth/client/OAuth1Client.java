package com.zero.oauth.client;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.IResponseProperty;
import com.zero.oauth.client.type.HttpMethod;

public class OAuth1Client<T extends OAuth1Api> extends OAuthClient<T> {

    public String generateAuthorizeRedirect() {
        return null;
    }

    public <P extends IResponseProperty> IPropertyStore<P> fetchAccessToken() {
        return null;
    }

    @Override
    public <P extends IResponseProperty, R extends IPropertyModel> IPropertyStore<P> request(HttpMethod method,
                                                                                             String url,
                                                                                             IPropertyStore<R> reqProps) {
        return null;
    }

}
