package com.zero.oauth.client;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.client.type.OAuthVersion;

public class OAuth1ApiTest {

    @Test
    public void init() {
        OAuthApi api = OAuthApi.initV1("clientId", "clientSecret", "requestTokenUrl", "authorizeUrl", "accessTokenUrl")
                               .registerApiBaseUrl("apiBaseUrl");
        Assert.assertEquals("clientId", api.getClientId());
        Assert.assertEquals("clientSecret", api.getClientSecret());
        Assert.assertEquals("requestTokenUrl", api.getRequestTokenUrl());
        Assert.assertEquals("authorizeUrl", api.getAuthorizeUrl());
        Assert.assertEquals("accessTokenUrl", api.getAccessTokenUrl());
        Assert.assertEquals("apiBaseUrl", api.getApiBaseUrl());
        Assert.assertNull(api.getRefreshTokenUrl());
        Assert.assertTrue(api.getRequestProperties().getVersion().isEqual(OAuthVersion.V1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void init_withEmptyRequiredValue() {
        OAuthApi.initV1(null, null, null, "authorizeUrl", "accessTokenUrl");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void init_withRefreshTokenUrl() {
        OAuthApi.initV1("clientId", "clientSecret", "requestTokenUrl", "authorizeUrl", "accessTokenUrl")
                .registerRefreshTokenUrl("refresh");
    }

}
