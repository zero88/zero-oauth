package com.zero.oauth.client;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.type.OAuthVersion;

public class OAuth1ApiTest extends TestBase {

    @Test
    public void test_init() {
        OAuthApi api = OAuthApi.initV1("clientId", "clientSecret", "requestTokenUrl", "authorizeUrl", "accessTokenUrl");
        Assert.assertEquals("clientId", api.getClientId());
        Assert.assertEquals("clientSecret", api.getClientSecret());
        Assert.assertEquals("requestTokenUrl", api.getRequestTokenUrl());
        Assert.assertEquals("authorizeUrl", api.getAuthorizeUrl());
        Assert.assertEquals("accessTokenUrl", api.getAccessTokenUrl());
        Assert.assertNull(api.getRefreshTokenUrl());
        Assert.assertTrue(api.getVersion().isEqual(OAuthVersion.V1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_init_withEmptyRequiredValue() {
        OAuthApi.initV1(null, null, null, "authorizeUrl", "accessTokenUrl");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_init_withRefreshTokenUrl() {
        OAuthApi.initV1("clientId", "clientSecret", "requestTokenUrl", "authorizeUrl", "accessTokenUrl")
                .refreshTokenUrl("refresh");
    }

    @Test
    public void test_init_withRegister() {
        OAuthApi api = OAuthApi.initV1("1", "2", "3", "4", "5").apiBaseUrl("apiBaseUrl");
        Assert.assertEquals("apiBaseUrl", api.getApiBaseUrl());
    }

}
