package com.zero.oauth.core.exceptions;

/**
 * Represents a problem in the OAuth URL.
 */
public class OAuthUrlException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OAuthUrlException(String message, Exception e) {
        super(message, e);
    }

    public OAuthUrlException(String message) {
        super(message, null);
    }

    public OAuthUrlException(Exception e) {
        super(e);
    }

}
