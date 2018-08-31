package com.zero.oauth.core.security;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Objects;

import com.zero.oauth.core.exceptions.OAuthSignatureException;
import com.zero.oauth.core.type.SignatureMethod;
import com.zero.oauth.core.utils.Strings;

/**
 * A signature service that uses the RSA-SHA1 algorithm.
 *
 * @see SignatureMethod#RSA_SHA1
 */
public final class RSASha1SignatureService implements SignatureService {

    private final PrivateKey privateKey;

    public RSASha1SignatureService(PrivateKey privateKey) {
        if (Objects.isNull(privateKey)) {
            throw new OAuthSignatureException(getSignatureMethod(), null, "Private Key is null");
        }
        this.privateKey = privateKey;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec No need {@code consumer secret} and {@code token secret}.
     */
    @Override
    public String computeSignature(String baseString, String consumerSecret, String tokenSecret) {
        try {
            final Signature signature = Signature.getInstance(getSignatureMethod().getAlgorithm());
            signature.initSign(privateKey);
            signature.update(Strings.requireNotBlank(baseString).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | RuntimeException e) {
            throw new OAuthSignatureException(getSignatureMethod(), baseString, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignatureMethod getSignatureMethod() {
        return SignatureMethod.RSA_SHA1;
    }

}
