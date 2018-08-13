package com.zero.oauth.client;

import com.zero.oauth.client.core.properties.ResponseProperties;
import com.zero.oauth.client.exceptions.OAuthSecurityException;

/**
 * Callback handler to complete the OAuth flow. It is extension point to attach into existing server.
 *
 * @since 1.0.0
 */
public interface ICallbackHandler {

    /**
     * It is an URL that tells the Authorization server where to send the user back to after they approve the request or
     * after exchange {@code Authorization code} for an {@code Access Token}.
     *
     * @return callback/redirect url
     * @see #handleAuthorizationStep(ResponseProperties)
     * @see #handleExchangeAccessToken(ResponseProperties)
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
     * Handle the Authorization server response after an user approves the request. It should dispatch HTTP request to
     * navigate user if any error.
     *
     * @param response The Authorization server response
     * @throws OAuthSecurityException If not handle error response
     * @see ResponseProperties
     */
    default void handleAuthorizationStep(ResponseProperties response) {
        if (response.isError()) {
            throw new OAuthSecurityException("Error");
        }
    }

    /**
     * Handle the Authorization server's response after exchanged the {@code Authorization code} for an {@code Access
     * Token}. This step should verify the {@code Access Token} and mapping user profile with the Authorization server.
     *
     * @param response The Authorization server response
     * @throws OAuthSecurityException If not handle error response
     * @see ResponseProperties
     */
    default void handleExchangeAccessToken(ResponseProperties response) {
        if (response.isError()) {
            throw new OAuthSecurityException("Error");
        }
    }

}
