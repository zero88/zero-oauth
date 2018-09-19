package com.zero.oauth.client.http;

import com.zero.oauth.core.exceptions.OAuthException;
import com.zero.oauth.core.exceptions.OAuthHttpException;

/**
 * HTTP Callback in asynchronous mode.
 *
 * @since 1.0.0
 */
public interface HttpCallback {

    /**
     * Handle data when the HTTP response was successfully returned by the remote server.
     *
     * @param response Response data
     * @see HttpData
     */
    void onSuccess(HttpData response);

    /**
     * Handle error when the request could not be executed due to cancellation, a connectivity problem or * timeout.
     *
     * @param exception HTTP exception
     * @see OAuthException
     */
    void onFailed(OAuthHttpException exception);

}
