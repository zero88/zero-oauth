package com.zero.oauth.client.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.zero.oauth.client.exceptions.OAuthSecurityException;
import com.zero.oauth.client.utils.Constants;
import com.zero.oauth.client.utils.Environments;
import com.zero.oauth.client.utils.FileUtils;
import com.zero.oauth.client.utils.Strings;

/**
 * Defines a set of method to generate token key or load private key from file. The implementation of this
 * interface must have {@code Constructor} with no arguments.
 *
 * @since 1.0.0
 */
public interface SecurityService {

    /**
     * Load RSA Private Key from environment variable or configuration variable.
     *
     * @return private key
     * @throws OAuthSecurityException if error when finding or reading file.
     * @see PrivateKey
     */
    static PrivateKey loadRsaPrivateKey() {
        try {
            String privateKeyFile = Environments.getVar(Constants.OAUTH_SIGNATURE_RSA_PRIVATE_KEY_FILE);
            String fileContent = FileUtils.readFileToString(privateKeyFile);
            fileContent = fileContent.replaceAll("^-+BEGIN (RSA\\s)?PRIVATE KEY-+\n", "")
                                     .replaceAll("\n-+END (RSA\\s)?PRIVATE KEY-+", "");
            final KeyFactory fac = KeyFactory.getInstance("RSA");
            final PKCS8EncodedKeySpec privKeySpec =
                new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(fileContent));
            return fac.generatePrivate(privKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | RuntimeException e) {
            throw new OAuthSecurityException("Cannot read private key", e);
        }
    }

    static int loadRandomTextMaxLength() {
        return Strings.convertToInt(Environments.getVar(Constants.OAUTH_RANDOM_TEXT_MAX_LEN_PROPERTY), 20);
    }

    static String loadRandomTextSymbols() {
        return Environments.getVar(Constants.OAUTH_RANDOM_TEXT_SYMBOL_PROPERTY);
    }

    /**
     * Returns the unix epoch timestamp in seconds.
     *
     * @return timestamp
     */
    default String getTimestampInSeconds() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * Random token as unique value for each request.
     *
     * @return random token
     */
    String randomToken();

}
