package com.zero.oauth.client;

import java.util.EnumMap;
import java.util.Map;

import com.zero.oauth.core.converter.HttpHeaderConverter;
import com.zero.oauth.core.converter.HttpQueryConverter;
import com.zero.oauth.core.converter.JsonConverter;
import com.zero.oauth.core.exceptions.OAuthException;
import com.zero.oauth.core.exceptions.OAuthSecurityException;
import com.zero.oauth.core.properties.GenericResponseStore;
import com.zero.oauth.core.properties.IResponseProperties;
import com.zero.oauth.core.properties.IResponseProperty;
import com.zero.oauth.core.type.HttpPlacement;
import com.zero.oauth.core.type.OAuthVersion;

/**
 * Callback handler to complete the OAuth flow. It is extension point to attach into existing server.
 *
 * @since 1.0.0
 */
public interface ICallbackHandler {

    /**
     * OAuth version is inherited from {@code OAuthApi}.
     *
     * @return OAuth version
     */
    OAuthVersion getVersion();

    /**
     * It is an URL that tells the Authorization server where to send the user back to after they approve the request or
     * after exchange {@code Authorization code} for an {@code Access Token}.
     *
     * @return callback/redirect url
     * @see #handleAuthorizationStep(String, String)
     * @see #handleExchangeAccessToken(String, String, String)
     */
    String getCallbackUrl();

    /**
     * Redirect user to login screen to get their permission.
     *
     * @param authorizationUrl The authorization server url that prompts an user grants permission for application's
     *                         request
     */
    void redirectUserToLoginScreen(String authorizationUrl);

    /**
     * Handle the Authorization server response after an user approves the request. It will be called in the {@code
     * Application server} or {@code client tool}. Its duty is mainly validation and extraction the {@code Authorization
     * code} from HTTP Request.
     *
     * @param header HTTP header
     * @param query  HTTP query
     * @return Response properties
     * @throws OAuthSecurityException If any security point is invalid
     * @throws OAuthException         If OAuth client or server configuration is error or cannot connect to OAuth
     *                                server
     * @see IResponseProperty
     * @see IResponseProperties
     */
    default GenericResponseStore handleAuthorizationStep(String header, String query) {
        EnumMap<HttpPlacement, Map<String, Object>> data = new EnumMap<>(HttpPlacement.class);
        data.put(HttpPlacement.HEADER, new HttpHeaderConverter().deserialize(header));
        data.put(HttpPlacement.URI_QUERY, new HttpQueryConverter().deserialize(query));
        return new GenericResponseStore(getVersion(), data);
    }

    /**
     * Handle the Authorization server's response after exchanged the {@code Authorization code} for an {@code Access
     * Token}. This step should verify the {@code Access Token} and mapping user profile with the Authorization server.
     *
     * @param header HTTP header
     * @param query  HTTP query
     * @param body   HTTP body
     * @return Response properties
     * @throws OAuthSecurityException If any security point is invalid
     * @throws OAuthException         If OAuth client or server configuration is error or cannot connect to OAuth
     *                                server
     * @see IResponseProperty
     * @see IResponseProperties
     */
    default GenericResponseStore handleExchangeAccessToken(String header, String query, String body) {
        EnumMap<HttpPlacement, Map<String, Object>> data = new EnumMap<>(HttpPlacement.class);
        data.put(HttpPlacement.HEADER, new HttpHeaderConverter().deserialize(header));
        data.put(HttpPlacement.URI_QUERY, new HttpQueryConverter().deserialize(query));
        data.put(HttpPlacement.BODY, new JsonConverter().deserialize(body));
        return new GenericResponseStore(getVersion(), data);
    }

}
