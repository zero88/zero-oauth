package com.zero.oauth.client.exceptions;

import com.zero.oauth.client.type.SignatureMethod;

/**
 * Specialized exception that represents a problem in the signature.
 */
public class OAuthSignatureException extends OAuthException {

    private static final long serialVersionUID = 1L;
    private static final String MSG = "Error while signing string: %s";

    /**
     * Constructor for signature exception.
     *
     * @param signature Signature gets signed (HMAC-SHA, etc)
     * @param e         original exception
     *
     * @see SignatureMethod
     */
    public OAuthSignatureException(SignatureMethod signature, Exception e) {
        super(String.format(MSG, signature), e);
    }

}
