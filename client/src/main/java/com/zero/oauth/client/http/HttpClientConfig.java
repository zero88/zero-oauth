package com.zero.oauth.client.http;

import java.net.Proxy;

/**
 * HTTP Client configuration.
 *
 * @since 1.0.0
 */
public interface HttpClientConfig {

    int DEFAULT_CONNECT_TIME_OUT = 60;
    int DEFAULT_READ_TIME_OUT = 0;
    String DEFAULT_USER_AGENT = null;
    boolean DEFAULT_FOLLOW_REDIRECT = true;
    Proxy DEFAULT_PROXY = null;

    /**
     * Get HTTP client configuration.
     *
     * @param key Configuration key
     * @param <V> Any type
     * @return config, {@code null} if not found.
     */
    <V> V getConfig(String key);

    /**
     * Add HTTP client configuration.
     *
     * @param key    Configuration key
     * @param config Configuration
     * @param <V>    Any Type of configuration object
     * @param <C>    Implementation Type of {@link HttpClientConfig}
     * @return {@code this}
     */
    <V, C extends HttpClientConfig> C addConfig(String key, V config);

    /**
     * Timeout in seconds to be used when opening a communications link to the resource referenced.
     *
     * @return connect timeout
     */
    default int getConnectTimeout() {
        return DEFAULT_CONNECT_TIME_OUT;
    }

    /**
     * Read timeout in seconds.A non-zero value specifies the timeout when reading from Input stream when a connection
     * is established to a resource. A timeout of zero is interpreted as an infinite timeout.
     *
     * @return read timeout
     */
    default int getReadTimeout() {
        return DEFAULT_READ_TIME_OUT;
    }

    /**
     * Get user agent.
     *
     * @return user-agent
     */
    default String getUserAgent() {
        return DEFAULT_USER_AGENT;
    }

    /**
     * Identifies whether HTTP redirects (requests with response code 3xx) should be automatically followed.
     *
     * @return {@code True} if follow redirect, else otherwise
     */
    default boolean isFollowRedirect() {
        return DEFAULT_FOLLOW_REDIRECT;
    }

    /**
     * Get Proxy.
     *
     * @return {@code null} if not use proxy.
     * @see Proxy
     */
    default Proxy getProxy() {
        return DEFAULT_PROXY;
    }

}
