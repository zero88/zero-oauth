package com.zero.oauth.client.http;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.zero.oauth.core.exceptions.OAuthHttpException;
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
     * @param url         Host URL (Note: it is not included HTTP Query)
     * @param method      HTTP Method
     * @param requestData HTTP Request Data
     * @return Response HTTP Data
     * @throws OAuthHttpException if any error
     * @see HttpMethod
     * @see HttpData
     */
    HttpData execute(String url, HttpMethod method, HttpData requestData);

    /**
     * Send asynchronous HTTP Request to given URL.
     *
     * @param url         Host URL (Note: it is not included HTTP Query)
     * @param method      HTTP Method
     * @param requestData HTTP Request Data
     * @return Future of Response HTTP Data
     * @throws OAuthHttpException if any error
     * @see HttpMethod
     * @see HttpData
     * @see Future
     */
    Future<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestData);

    /**
     * Send asynchronous HTTP Request to given URL.
     *
     * @param url         Host URL (Note: it is not included HTTP Query)
     * @param method      HTTP Method
     * @param requestData HTTP Request Data
     * @param callback    Callback to handle result after executing request
     * @see HttpMethod
     * @see HttpData
     * @see HttpCallback
     */
    void asyncExecute(String url, HttpMethod method, HttpData requestData, HttpCallback callback);

    /**
     * Send asynchronous HTTP Request to given URL.
     *
     * @param url            Host URL (Note: it is not included HTTP Query)
     * @param method         HTTP Method
     * @param requestData    HTTP Request Data
     * @param resultNullable If any error occurs when executing, with {@code resultNullable = true}, HTTP client will
     *                       log error and return {@code null} for {@code consumer}; if try to use {@link
     *                       CompletableFuture#get()}, it will throws {@link ExecutionException}
     * @return Future of Response HTTP Data
     * @see HttpMethod
     * @see HttpData
     * @see CompletableFuture
     */
    CompletableFuture<HttpData> asyncExecute(String url, HttpMethod method, HttpData requestData,
                                             boolean resultNullable);

}
