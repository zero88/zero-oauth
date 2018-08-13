package com.zero.oauth.client.security;

import com.zero.oauth.client.exceptions.OAuthException;
import com.zero.oauth.client.exceptions.OAuthSignatureException;
import com.zero.oauth.client.type.SignatureMethod;
import com.zero.oauth.client.utils.Constants;
import com.zero.oauth.client.utils.Strings;
import com.zero.oauth.client.utils.Urls;

/**
 * Plaintext implementation of {@link SignatureService}.
 *
 * @see SignatureMethod#PLAIN_TEXT
 */
public final class PlainTextSignatureService implements SignatureService {

    /**
     * {@inheritDoc}
     *
     * @implSpec No need {@code base string}
     */
    @Override
    public String computeSignature(String baseString, String consumerSecret, String tokenSecret) {
        try {
            String encodedToken = Strings.isBlank(tokenSecret) ? Constants.BLANK : Urls.encode(tokenSecret);
            return Urls.encode(Strings.requireNotBlank(consumerSecret, "Consumer secret can not be blank")) + '&' +
                   encodedToken;
        } catch (OAuthException | IllegalArgumentException e) {
            throw new OAuthSignatureException(getSignatureMethod(), "", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignatureMethod getSignatureMethod() {
        return SignatureMethod.PLAIN_TEXT;
    }

}
