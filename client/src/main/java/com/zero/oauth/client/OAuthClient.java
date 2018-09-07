package com.zero.oauth.client;

import java.util.Objects;

import com.zero.oauth.client.http.HttpClient;
import com.zero.oauth.client.http.JdkHttpClient;
import com.zero.oauth.core.Logger;
import com.zero.oauth.core.LoggerFactory;

import lombok.RequiredArgsConstructor;

public interface OAuthClient {

    String LOGGER_NAME = "com.zero.oauth.client";

    static <T extends OAuthApi> Builder<T> builder(T api) {
        return new Builder<>(Objects.requireNonNull(api));
    }

    /**
     * Retrieve OAuth API.
     *
     * @param <T> Type of OAuth API
     * @return OAuth API instance
     * @see OAuthApi
     */
    <T extends OAuthApi> T getApi();

    /**
     * Strict mode that means all {@code recommendation} properties are treated as {@code mandatory} properties.
     *
     * @return {@code True} if client is in strict mode, else otherwise
     */
    boolean isStrict();

    ICallbackHandler getCallback();

    HttpClient getHttpClientExecutor();

    /**
     * Turn on {@code Async} request.
     */
    void turnOnAsync();

    /**
     * Turn off {@code Async} request.
     */
    void turnOffAsync();

    @RequiredArgsConstructor
    class Builder<T extends OAuthApi> {

        private final T api;
        private boolean strict = false;
        private ICallbackHandler callback;
        private Logger logger;
        private HttpClient httpClientExecutor;
        private boolean async = true;

        public Builder strict(boolean strict) {
            this.strict = strict;
            return this;
        }

        public Builder callback(ICallbackHandler callback) {
            this.callback = Objects.requireNonNull(callback);
            return this;
        }

        public Builder callbackUrl(String callbackUrl) {
            this.callback = new CallbackHandler(this.api.getVersion(), callbackUrl);
            return this;
        }

        public Builder logger(Logger logger) {
            this.logger = Objects.requireNonNull(logger);
            return this;
        }

        public Builder httpClientExecutor(HttpClient httpClientExecutor) {
            this.httpClientExecutor = Objects.requireNonNull(httpClientExecutor);
            return this;
        }

        public Builder asyncHttp(boolean async) {
            this.async = async;
            return this;
        }

        public OAuthClient build() {
            if (Objects.isNull(this.logger)) {
                LoggerFactory.initialize(LOGGER_NAME);
            } else {
                LoggerFactory.initialize(this.logger);
            }
            HttpClient httpClient = Objects.isNull(this.httpClientExecutor)
                                            ? new JdkHttpClient()
                                            : httpClientExecutor;
            if (this.api instanceof OAuth1Api) {
                return new OAuth1Client((OAuth1Api) api, strict, callback, httpClient, async);
            }
            return new OAuth2Client((OAuth2Api) api, strict, callback, httpClient, async);
        }

    }

}
