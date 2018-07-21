package com.zero.oauth.client.exceptions;

/**
 * Represents a problem in the OAuth signing process
 */
public class OAuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OAuthException(String message, Exception e) {
        super(message, e);
    }

    public OAuthException(String message) {
        super(message, null);
    }

    public OAuthException(Exception e) {
        super(e);
    }
}
