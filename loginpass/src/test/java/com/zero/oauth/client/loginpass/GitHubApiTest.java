package com.zero.oauth.client.loginpass;

import java.io.IOException;

import org.junit.Before;

import com.zero.oauth.client.OAuthClient;
import com.zero.oauth.core.utils.Environments;
import com.zero.oauth.server.MockApplicationServer;

public class GitHubApiTest {

    private static final String OAUTH_CALLBACK_PATH = "/api/oauth/callback";
    private MockApplicationServer server;
    private OAuthClient client;

    @Before
    public void setUp() throws IOException {
        server = new MockApplicationServer(8080, OAUTH_CALLBACK_PATH);
        client = OAuthClient.builder(
            new GitHubApi(Environments.getVar("GITHUB_CLIENT_ID"), Environments.getVar("GITHUB_CLIENT_SECRET")))
                            .callbackUrl(server.getCallbackUrl()).build();
        server.registerCallback(client.getCallback()).start();
    }

}
