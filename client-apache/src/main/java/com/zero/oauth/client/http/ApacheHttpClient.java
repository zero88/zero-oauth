package com.zero.oauth.client.http;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.zero.oauth.core.type.HttpMethod;

public class ApacheHttpClient implements HttpClient {

    @Override
    public <C extends HttpClientConfig> C getConfig() {
        return null;
    }

    @Override
    public HttpData execute(String url, HttpMethod method, HttpData requestData) {
        return null;
    }

    @Override
    public Future<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestData) {
        return null;
    }

    @Override
    public void asyncExecute(String url, HttpMethod method, HttpData requestData, HttpCallback callback) {

    }

    @Override
    public CompletableFuture<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestData, boolean resultNullable) {
        return null;
    }

}
