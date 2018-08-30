package com.zero.oauth.client;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.type.GrantType;
import com.zero.oauth.server.MockApplicationServer;

public class OAuth2ClientTest {

    private static final String OAUTH_CALLBACK_PATH = "/api/oauth/callback";
    private OAuthClient client;

    @Before
    public void setUp() throws IOException {
        MockApplicationServer server = new MockApplicationServer(8080, OAUTH_CALLBACK_PATH);
        OAuth2Api api = OAuthApi.initV2("clientId", "clientSecret", "authorizeUrl", "accessTokenUrl",
                                        GrantType.AUTH_CODE);
        client = OAuthClient.builder(api).callbackUrl(server.getCallbackUrl()).build();
        server.registerCallback(client.getCallback()).start();
    }

    @Test
    public void test_authorization() {
        //        Future<IResponseProperties> result = client.authorize();
        //        try {
        //            IResponseProperties store = result.get(1, TimeUnit.MINUTES);
        //            Map<String, Object> response = store.toMap();
        //            Assert.assertTrue(response.containsKey("error"));
        //            Assert.assertEquals("redirect_uri_mismatch", response.get("error"));
        //            Assert.assertTrue(response.containsKey("error_description"));
        //            Assert.assertEquals("The redirect_uri MUST match the registered callback URL for this application.",
        //                                response.get("error_description"));
        //            Assert.assertTrue(response.containsKey("error_uri"));
        //            //            Assert.assertEquals(
        //            //                "https://developer.github.com/apps/managing-oauth-apps/troubleshooting-authorization-request-errors/#redirect-uri-mismatch&state=9d3S3nPPT7fL8p3YKwA8jMT3SW",
        //            //                response.get("error_uri"));
        //        } catch (InterruptedException | ExecutionException | TimeoutException e) {
        //            e.printStackTrace();
        //        }
    }

}
