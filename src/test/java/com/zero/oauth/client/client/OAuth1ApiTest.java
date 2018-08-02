package com.zero.oauth.client.client;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.client.type.OAuthVersion;

public class OAuth1ApiTest {

    @Test
    public void init() {
        OAuthApi api =
                OAuthApi.init("clientId", "clientSecret", "requestTokenUrl", "authorizeUrl", "accessTokenUrl")
                        .registerApiBaseUrl("apiBaseUrl");
        Assert.assertEquals("clientId", api.getClientId());
        Assert.assertEquals("clientSecret", api.getClientSecret());
        Assert.assertEquals("requestTokenUrl", api.getRequestTokenUrl());
        Assert.assertEquals("authorizeUrl", api.getAuthorizeUrl());
        Assert.assertEquals("accessTokenUrl", api.getAccessTokenUrl());
        Assert.assertEquals("apiBaseUrl", api.getApiBaseUrl());
        Assert.assertTrue(api.getDefaultPayload().getVersion().isEqual(OAuthVersion.V1));
        Assert.assertNull(api.getRefreshTokenUrl());
    }

    @Test(expected = IllegalArgumentException.class)
    public void init_withEmptyRequiredValue() {
        OAuthApi.init(null, null, null, "authorizeUrl", "accessTokenUrl");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void init_withRefreshTokenUrl() {
        OAuthApi.init("clientId", "clientSecret", "requestTokenUrl", "authorizeUrl", "accessTokenUrl")
                .registerRefreshTokenUrl("refresh");
    }

}
