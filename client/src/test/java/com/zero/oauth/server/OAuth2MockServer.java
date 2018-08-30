package com.zero.oauth.server;

import java.io.IOException;

import lombok.RequiredArgsConstructor;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@RequiredArgsConstructor
public final class OAuth2MockServer {

    public static final String AUTHORIZATION_PATH = "/api/auth/authorize";
    public static final String API_CHECK_VERSION = "/api/check/version/";
    public static final String API_PROFILE_INFO = "/api/profile/info";
    private final int port;
    private final MockWebServer server = new MockWebServer();

    public static void main(String[] args) throws IOException {
        new OAuth2MockServer(9000).start();
    }

    public void start() throws IOException {
        this.server.setDispatcher(getDispatcher());
        this.server.start(this.port);
    }

    private Dispatcher getDispatcher() {
        return new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                String path = request.getPath();
                if (AUTHORIZATION_PATH.equals(path)) {
                    return new MockResponse().setResponseCode(200);
                }
                if (API_CHECK_VERSION.equals(path)) {
                    return new MockResponse().setResponseCode(200).setBody("version=9");
                }
                if (API_PROFILE_INFO.equals(path)) {
                    return new MockResponse().setResponseCode(200).setBody(
                        "{\"info\":{\"getName\":\"Lucas Albuquerque\",\"age\":\"21\",\"gender\":\"male\"}}");
                }
                return new MockResponse().setResponseCode(404);
            }
        };
    }

}
