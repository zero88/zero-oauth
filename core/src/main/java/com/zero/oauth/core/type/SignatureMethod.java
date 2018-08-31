package com.zero.oauth.core.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * OAuth {@code 1.0a} signature method.
 *
 * @see OAuthVersion#V1
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SignatureMethod {

    /**
     * {@code HMAC-SHA1} signature algorithm where the Signature Base String is the text and the key is the concatenated
     * values (each first encoded per Parameter Encoding) of the Consumer Secret and Token Secret, separated by an
     * '&amp;' character (ASCII code 38) even if empty.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.4.2">[RFC5849 - OAuth1] HMAC-SHA1</a>
     * @see <a href="https://tools.ietf.org/html/rfc2104">[RFC2104] HMAC: Keyed-Hashing for Message
     *         Authentication</a>
     */
    HMAC_SHA1("HMAC-SHA1", "HmacSHA1"),

    /**
     * {@code RSA-SHA1} signature method uses the RSASSA-PKCS1-v1_5 signature algorithm as defined in [RFC3447] section
     * 8.2 (more simply known as PKCS#1), using SHA-1 as the hash function for EMSA-PKCS1-v1_5. It is assumed that the
     * Consumer has provided its RSA public key in a verified way to the Service Provider, in a manner which is beyond
     * the scope of this specification.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.4.3">[RFC5849 - OAuth1] RSA-SHA1</a>
     * @see <a href="https://tools.ietf.org/html/rfc8017">[RFC8017] RSA Cryptography Specifications Version 2.2</a>
     */
    RSA_SHA1("RSA-SHA1", "SHA1withRSA"),

    /**
     * The PLAINTEXT method does not provide any security protection and SHOULD only be used over a secure channel such
     * as HTTPS. It does not use the Signature Base String.
     *
     * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.4.4">[RFC5849 - OAuth1] PLAINTEXT</a>
     */
    PLAIN_TEXT("PLAINTEXT", null);

    private final String name;
    private final String algorithm;

    @Override
    public String toString() {
        return this.name;
    }
}
