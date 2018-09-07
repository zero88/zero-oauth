package com.zero.oauth.client.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.Proxy;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;

public class JdkHttpClientConfigTest extends TestBase {

    private JdkHttpClientConfig config;
    private JdkHttpClientConfig customConfig;

    @Before
    public void setUp() {
        config = new JdkHttpClientConfig();
        customConfig = new JdkHttpClientConfig().addConfig(JdkHttpClientConfig.USER_AGENT, "JdkHttpClient").addConfig(
            JdkHttpClientConfig.FOLLOW_REDIRECT, false).addConfig(JdkHttpClientConfig.PROXY, Proxy.NO_PROXY).addConfig(
            JdkHttpClientConfig.TIMEOUT, 120).addConfig(JdkHttpClientConfig.READ_TIMEOUT, 60);
    }

    @Test
    public void testConnectTimeout() {
        assertEquals(60, config.getConnectTimeout());
        assertEquals(120, customConfig.getConnectTimeout());
    }

    @Test
    public void testReadTimeOut() {
        assertEquals(0, config.getReadTimeout());
        assertEquals(60, customConfig.getReadTimeout());
    }

    @Test
    public void testFollowRedirect() {
        assertTrue(config.isFollowRedirect());
        assertFalse(customConfig.isFollowRedirect());
    }

    @Test
    public void testUserAgent() {
        assertNull(config.getUserAgent());
        assertEquals("JdkHttpClient", customConfig.getUserAgent());
    }

    @Test
    public void testProxy() {
        assertNull(config.getProxy());
        assertEquals(Proxy.NO_PROXY, customConfig.getProxy());
    }
}
