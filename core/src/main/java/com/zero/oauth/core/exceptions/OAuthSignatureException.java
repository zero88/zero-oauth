package com.zero.oauth.core.exceptions;

import com.zero.oauth.core.type.SignatureMethod;

/**
 * Specialized exception that represents a problem in the signature.
 */
public class OAuthSignatureException extends OAuthSecurityException {

    private static final long serialVersionUID = 1L;
    private static final String MSG = "Error while signing string: %s with method: %s";
    private static final String ERROR_MSG = "Signature method: %s. Error: %s";

    /**
     * Constructor for Signature exception.
     *
     * @param method Signature gets signed (HMAC-SHA, etc)
     * @param e      original exception
     * @param error  Detailed error message
     * @see SignatureMethod
     */
    public OAuthSignatureException(SignatureMethod method, Exception e, String error) {
        super(String.format(ERROR_MSG, method, error), e);
    }

    /**
     * Constructor for Signature exception when signing.
     *
     * @param method Signature method
     * @param base   Base string to get signed
     * @param e      original exception
     * @see SignatureMethod
     */
    public OAuthSignatureException(SignatureMethod method, String base, Exception e) {
        super(String.format(MSG, method, base), e);
    }

}
