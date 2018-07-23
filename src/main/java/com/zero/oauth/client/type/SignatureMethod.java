package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * OAuth {@code 1.0a} signature method.
 *
 * @see OAuthVersion#V1
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(doNotUseGetters = true, includeFieldNames = false, onlyExplicitlyIncluded = true)
public enum SignatureMethod {

    /**
     * {@code HMAC-SHA1} signature algorithm where the Signature Base String is the text and the key is the
     * concatenated values (each first encoded per Parameter Encoding) of the Consumer Secret and Token Secret,
     * separated by an '&' character (ASCII code 38) even if empty.
     * <ul>
     * <li>More in <a href="https://oauth.net/core/1.0a/#anchor15">OAuth HMAC-SHA1</a></li>
     * <li>More in <a href="https://oauth.net/core/1.0a/#RFC2104">[RFC2104]</a></li>
     * </ul>
     */
    HMAC_SHA1("HMAC-SHA1"),

    /**
     * {@code RSA-SHA1} signature method uses the RSASSA-PKCS1-v1_5 signature algorithm as defined in [RFC3447]
     * section 8.2 (more simply known as PKCS#1), using SHA-1 as the hash function for EMSA-PKCS1-v1_5. It is assumed
     * that the
     * Consumer has provided its RSA public key in a verified way to the Service Provider, in a manner which is beyond
     * the scope of this specification.
     * <ul>
     * <li>More in <a href="https://oauth.net/core/1.0a/#anchor18">OAuth RSA-SHA1</a></li>
     * <li>More in <a href="https://oauth.net/core/1.0a/#RFC3447">[RFC3447]</a></li>
     * </ul>
     */
    RSA_SHA1("RSA-SHA1"),

    /**
     * The PLAINTEXT method does not provide any security protection and SHOULD only be used over a secure channel such
     * as HTTPS. It does not use the Signature Base String.
     * <ul>
     * <li>More in <a href="https://oauth.net/core/1.0a/#anchor21">OAuth PLAINTEXT</a></li>
     * </ul>
     */
    PLAIN_TEXT("PLAINTEXT");

    @ToString.Include
    private final String name;
}
