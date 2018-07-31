package com.zero.oauth.client.client;

import org.junit.Assert;
import org.junit.Test;

public class OAuth2ClientTest {

    @Test
    public void init() {
        IOAuthClient client = IOAuthClient
                .init("clientId", "clientSecret", "authorizeUrl", "accessTokenUrl", "apiBaseUrl");
        Assert.assertEquals("clientId", client.getClientId());
        Assert.assertEquals("clientSecret", client.getClientSecret());
        Assert.assertNull(client.getRequestTokenUrl());
        Assert.assertEquals("authorizeUrl", client.getAuthorizeUrl());
        Assert.assertEquals("accessTokenUrl", client.getAccessTokenUrl());
        Assert.assertEquals("apiBaseUrl", client.getApiBaseUrl());
    }

}
