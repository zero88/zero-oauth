package com.zero.oauth.core.exceptions;

public class OAuthHttpException extends OAuthException {

    private static final long serialVersionUID = 1L;

    public OAuthHttpException(String message, Exception e) {
        super(message, e);
    }

    public OAuthHttpException(String message) {
        super(message, null);
    }

    public OAuthHttpException(Exception e) {
        super(e);
    }

}
