package com.zero.oauth.client;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.type.OAuthVersion;

public class CallbackHandlerTest extends TestBase {

    private ICallbackHandler callback;

    @Before
    public void setup() {
        this.callback = new CallbackHandler(OAuthVersion.V2, "callbackUrl");
    }

    @Test
    public void test_CallbackUrl() {
        assertEquals(OAuthVersion.V2, this.callback.getVersion());
        assertEquals("callbackUrl", this.callback.getCallbackUrl());
    }

    @Test
    public void test_AuthorizationUrl() {
        this.callback.redirectUserToLoginScreen("hello");
        assertEquals("hello", ((CallbackHandler) this.callback).getAuthorizationUrl());
    }

}
