package com.zero.oauth.client.http;

import java.util.concurrent.Future;

import com.zero.oauth.core.type.HttpMethod;

public class ApacheHttpClient implements HttpClient {

    @Override
    public <C extends HttpClientConfig> C getConfig() {
        return null;
    }

    @Override
    public HttpData execute(String url, HttpMethod method, HttpData requestHttpData) {
        return null;
    }

    @Override
    public Future<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestHttpData) {
        return null;
    }

}
