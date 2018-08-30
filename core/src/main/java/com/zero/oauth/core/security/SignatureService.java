package com.zero.oauth.core.security;

import com.zero.oauth.core.type.SignatureMethod;

/**
 * Signs a base string, returning the OAuth signature.
 *
 * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.4">[RFC5849] Signature</a>
 */
public interface SignatureService {

    /**
     * Compute signature based on given input.
     *
     * @param baseString     url-encoded string to sign
     * @param consumerSecret api secret for your app
     * @param tokenSecret    token secret (maybe empty string for the request token step)
     * @return signature
     */
    String computeSignature(String baseString, String consumerSecret, String tokenSecret);

    /**
     * Signature method defines security algorithm.
     *
     * @return signature method
     * @see SignatureMethod
     */
    SignatureMethod getSignatureMethod();

}
