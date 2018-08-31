package com.zero.oauth.core.security;

import com.zero.oauth.core.exceptions.OAuthException;
import com.zero.oauth.core.exceptions.OAuthSignatureException;
import com.zero.oauth.core.type.SignatureMethod;
import com.zero.oauth.core.utils.Constants;
import com.zero.oauth.core.utils.Strings;
import com.zero.oauth.core.utils.Urls;

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
