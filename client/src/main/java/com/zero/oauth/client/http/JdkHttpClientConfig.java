package com.zero.oauth.client.http;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.core.properties.HeaderProperty;
import com.zero.oauth.core.type.HttpMethod;
import com.zero.oauth.core.utils.Reflections;

/**
 * JDK HTTP Client Config.
 *
 * @since 1.0.0
 */
public class JdkHttpClientConfig implements HttpClientConfig {

    public static final String TIMEOUT = "timeout";
    public static final String READ_TIMEOUT = "read_timeout";
    public static final String FOLLOW_REDIRECT = "follow_redirect";
    public static final String PROXY = "proxy";
    public static final String USER_AGENT = HeaderProperty.USER_AGENT.getName();
    private final Map<String, Object> configMap = new HashMap<>();

    public JdkHttpClientConfig() {
        fixJdkHttpMethods();
    }

    private void fixJdkHttpMethods() {
        String[] methods = Arrays.stream(HttpMethod.values()).map(HttpMethod::name).toArray(String[]::new);
        Reflections.updateConstants(HttpURLConnection.class, "methods", methods);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getConfig(String key) {
        return (V) this.configMap.get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V, C extends HttpClientConfig> C addConfig(String key, V config) {
        this.configMap.put(key, config);
        return (C) this;
    }

    @Override
    public int getConnectTimeout() {
        Object config = getConfig(TIMEOUT);
        return Objects.isNull(config) ? HttpClientConfig.DEFAULT_CONNECT_TIME_OUT : (Integer) config;
    }

    @Override
    public int getReadTimeout() {
        Object config = getConfig(READ_TIMEOUT);
        return Objects.isNull(config) ? HttpClientConfig.DEFAULT_READ_TIME_OUT : (Integer) config;
    }

    @Override
    public String getUserAgent() {
        return Objects.isNull(getConfig(USER_AGENT)) ? HttpClientConfig.DEFAULT_USER_AGENT : (String) getConfig(USER_AGENT);
    }

    @Override
    public boolean isFollowRedirect() {
        Object config = getConfig(FOLLOW_REDIRECT);
        return Objects.isNull(config) ? HttpClientConfig.DEFAULT_FOLLOW_REDIRECT : (Boolean) config;
    }

    @Override
    public Proxy getProxy() {
        return Objects.isNull(getConfig(PROXY)) ? HttpClientConfig.DEFAULT_PROXY : (Proxy) getConfig(PROXY);
    }

}
