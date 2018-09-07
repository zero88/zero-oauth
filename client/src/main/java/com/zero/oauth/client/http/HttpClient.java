package com.zero.oauth.client.http;

import java.util.concurrent.Future;

import com.zero.oauth.core.type.HttpMethod;

/**
 * HTTP Client.
 *
 * @since 1.0.0
 */
public interface HttpClient {

    /**
     * Get defined HTTP Client configuration that attached into HTTP Client.
     *
     * @param <C> Type of {@link HttpClientConfig}
     * @return config
     */
    <C extends HttpClientConfig> C getConfig();

    /**
     * Send synchronous HTTP Request to to given URL.
     *
     * @param url             Host URL (Note: it is not included HTTP Query)
     * @param method          HTTP Method
     * @param requestHttpData HTTP Request Data
     * @return Response HTTP Data
     * @see HttpMethod
     * @see HttpData
     */
    HttpData execute(String url, HttpMethod method, HttpData requestHttpData);

    /**
     * Send asynchronous HTTP Request to given URL.
     *
     * @param url             Host URL (Note: it is not included HTTP Query)
     * @param method          HTTP Method
     * @param requestHttpData HTTP Request Data
     * @return Future of Response HTTP Data
     * @see HttpMethod
     * @see HttpData
     */
    Future<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestHttpData);

}
