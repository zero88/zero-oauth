package com.zero.oauth.client;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.type.GrantType;
import com.zero.oauth.core.type.OAuthVersion;

public class OAuth2ApiTest extends TestBase {

    @Test
    public void test_init() {
        OAuthApi api = OAuthApi.initV2("clientId", "clientSecret", "authorizeUrl", "accessTokenUrl",
                                       GrantType.AUTH_CODE);
        Assert.assertEquals("clientId", api.getClientId());
        Assert.assertEquals("clientSecret", api.getClientSecret());
        Assert.assertNull(api.getRequestTokenUrl());
        Assert.assertEquals("authorizeUrl", api.getAuthorizeUrl());
        Assert.assertEquals("accessTokenUrl", api.getAccessTokenUrl());
        Assert.assertTrue(api.getVersion().isEqual(OAuthVersion.V2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_init_withEmptyRequiredValue() {
        OAuthApi.initV2("", "", "authorizeUrl", "accessTokenUrl", GrantType.AUTH_CODE);
    }

    @Test
    public void test_init_withRegister() {
        OAuthApi api = OAuthApi.initV2("clientId", "clientSecret", "authorizeUrl", "accessTokenUrl",
                                       GrantType.AUTH_CODE).refreshTokenUrl("refresh").apiBaseUrl("apiBaseUrl");
        Assert.assertEquals("apiBaseUrl", api.getApiBaseUrl());
        Assert.assertEquals("refresh", api.getRefreshTokenUrl());
    }

}
