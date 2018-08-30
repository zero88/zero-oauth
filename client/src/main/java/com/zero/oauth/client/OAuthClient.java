package com.zero.oauth.client;

import java.util.Objects;

import com.zero.oauth.client.http.HttpClientExecutor;
import com.zero.oauth.client.http.JdkHttpClientExecutor;
import com.zero.oauth.core.Logger;
import com.zero.oauth.core.LoggerFactory;

import lombok.RequiredArgsConstructor;

public interface OAuthClient {

    static <T extends OAuthApi> Builder<T> builder(T api) {
        return new Builder<>(Objects.requireNonNull(api));
    }

    <T extends OAuthApi> T getApi();

    ICallbackHandler getCallback();

    HttpClientExecutor getHttpClientExecutor();

    Logger getLogger();

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
        private ICallbackHandler callback;
        private Logger logger = new Logger.JdkLogger("com.zero.oauth.client");
        private HttpClientExecutor httpClientExecutor = new JdkHttpClientExecutor();
        private boolean async = true;

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

        public Builder httpClientExecutor(HttpClientExecutor httpClientExecutor) {
            this.httpClientExecutor = Objects.requireNonNull(httpClientExecutor);
            return this;
        }

        public Builder asyncHttp(boolean async) {
            this.async = async;
            return this;
        }

        public OAuthClient build() {
            LoggerFactory.initialize(this.logger);
            if (this.api instanceof OAuth1Api) {
                return new OAuth1Client((OAuth1Api) api, callback, logger, httpClientExecutor, async);
            }
            return new OAuth2Client((OAuth2Api) api, callback, logger, httpClientExecutor, async);
        }

    }

}
