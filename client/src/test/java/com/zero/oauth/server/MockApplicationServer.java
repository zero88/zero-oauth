package com.zero.oauth.server;

import java.io.IOException;
import java.util.Objects;

import com.zero.oauth.client.ICallbackHandler;
import com.zero.oauth.core.exceptions.OAuthException;
import com.zero.oauth.core.exceptions.OAuthSecurityException;
import com.zero.oauth.core.type.HttpMethod;

import lombok.RequiredArgsConstructor;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@RequiredArgsConstructor
public final class MockApplicationServer {

    private final int port;
    private final String callbackPath;
    private final MockWebServer server = new MockWebServer();
    private ICallbackHandler handler;

    public String getCallbackUrl() {
        return "http://localhost:" + this.port + this.callbackPath;
    }

    public MockApplicationServer registerCallback(ICallbackHandler handler) {
        this.handler = Objects.requireNonNull(handler, "Callback handler cannot be null");
        return this;
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
                HttpMethod method = HttpMethod.valueOf(request.getMethod());
                if (callbackPath.equals(path)) {
                    if (HttpMethod.GET == method) {
                        try {
                            handler.handleAuthorizationStep(request.getHeaders().toString(),
                                                            request.getRequestUrl().query());
                        } catch (OAuthSecurityException ex) {
                            return new MockResponse().setResponseCode(401);
                        } catch (OAuthException ex) {
                            return new MockResponse().setResponseCode(500);
                        }
                    }
                    if (HttpMethod.POST == method) {
                        return new MockResponse().setResponseCode(201);
                    }
                }
                return new MockResponse().setResponseCode(404);
            }
        };
    }

}
