package com.zero.oauth.client;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.type.OAuthVersion;

public class OAuth2ApiTest {

    @Test
    public void init() {
        OAuthApi api =
            OAuthApi.init("clientId", "clientSecret", "authorizeUrl", "accessTokenUrl", GrantType.AUTH_CODE)
                    .registerApiBaseUrl("apiBaseUrl").registerRefreshTokenUrl("refreshTokenUrl");
        Assert.assertEquals("clientId", api.getClientId());
        Assert.assertEquals("clientSecret", api.getClientSecret());
        Assert.assertNull(api.getRequestTokenUrl());
        Assert.assertEquals("authorizeUrl", api.getAuthorizeUrl());
        Assert.assertEquals("accessTokenUrl", api.getAccessTokenUrl());
        Assert.assertEquals("apiBaseUrl", api.getApiBaseUrl());
        Assert.assertEquals("refreshTokenUrl", api.getRefreshTokenUrl());
        Assert.assertTrue(api.getRequestProperties().getVersion().isEqual(OAuthVersion.V2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void init_withEmptyRequiredValue() {
        OAuthApi.init("", "", "authorizeUrl", "accessTokenUrl", GrantType.AUTH_CODE);
    }

}
