package com.zero.oauth.core.exceptions;

/**
 * Represents a problem in the OAuth Security algorithm.
 */
public class OAuthSecurityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OAuthSecurityException(String message, Exception e) {
        super(message, e);
    }

    public OAuthSecurityException(String message) {
        super(message, null);
    }

    public OAuthSecurityException(Exception e) {
        super(e);
    }

}
