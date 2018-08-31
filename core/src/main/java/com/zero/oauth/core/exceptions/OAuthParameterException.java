package com.zero.oauth.core.exceptions;

/**
 * Specialized exception that represents a problem in the parameter.
 */
public class OAuthParameterException extends OAuthException {

    private static final long serialVersionUID = 1L;

    public OAuthParameterException(String message, Exception e) {
        super(message, e);
    }

    public OAuthParameterException(String message) {
        super(message, null);
    }

    public OAuthParameterException(Exception e) {
        super(e);
    }

}
