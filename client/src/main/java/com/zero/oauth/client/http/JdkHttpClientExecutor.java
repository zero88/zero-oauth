package com.zero.oauth.client.http;

import com.zero.oauth.core.properties.GenericResponseStore;
import com.zero.oauth.core.type.HttpMethod;

public class JdkHttpClientExecutor implements HttpClientExecutor {

    @Override
    public GenericResponseStore execute(String url, HttpMethod method, String headers, String queries, String body) {
        return null;
    }

    @Override
    public GenericResponseStore asyncExecute(String url, HttpMethod method, String headers, String queries,
                                             String body) {
        return null;
    }

}
