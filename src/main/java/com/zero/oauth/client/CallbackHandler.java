package com.zero.oauth.client;

/**
 * Callback handler to complete the OAuth flow. It can be attached into current existed server.
 *
 * @since 1.0.0
 */
public interface CallbackHandler {

    String getCallBackUrl();

}
