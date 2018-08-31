package com.zero.oauth.core.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.zero.oauth.core.exceptions.OAuthSignatureException;
import com.zero.oauth.core.type.SignatureMethod;
import com.zero.oauth.core.utils.Constants;
import com.zero.oauth.core.utils.Strings;
import com.zero.oauth.core.utils.Urls;

/**
 * HMAC-SHA1 implementation of {@link SignatureService}.
 *
 * @see SignatureMethod#HMAC_SHA1
 * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.4.2">[RFC5849] HMAC-SHA1</a>
 */
public final class HMACSha1SignatureService implements SignatureService {

    /**
     * {@inheritDoc}
     */
    @Override
    public String computeSignature(String baseString, String consumerSecret, String tokenSecret) {
        try {
            String base = Strings.requireNotBlank(baseString, "Base string can not be blank");
            String secret = Strings.requireNotBlank(consumerSecret, "Consumer secret can not be blank");
            String encodedToken = Strings.isBlank(tokenSecret) ? Constants.BLANK : Urls.encode(tokenSecret);
            return doSign(base, Urls.encode(secret) + '&' + encodedToken);
        } catch (NoSuchAlgorithmException | InvalidKeyException | RuntimeException e) {
            throw new OAuthSignatureException(getSignatureMethod(), baseString, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignatureMethod getSignatureMethod() {
        return SignatureMethod.HMAC_SHA1;
    }

    private String doSign(String toSign, String keyString) throws NoSuchAlgorithmException, InvalidKeyException {
        final SecretKeySpec key = new SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8),
                                                    getSignatureMethod().getAlgorithm());
        final Mac mac = Mac.getInstance(getSignatureMethod().getAlgorithm());
        mac.init(key);
        final byte[] bytes = mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes).replace(Constants.CARRIAGE_RETURN, Constants.BLANK);
    }

}
