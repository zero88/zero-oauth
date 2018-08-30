package com.zero.oauth.core.security;

import java.security.spec.InvalidKeySpecException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthSecurityException;
import com.zero.oauth.core.exceptions.OAuthSignatureException;
import com.zero.oauth.core.type.SignatureMethod;

public class RSASha1SignatureServiceTest extends TestBase {

    private SignatureService signatureService;

    /**
     * Key generator.
     * <p>
     * {@code openssl req -x509 -nodes -days 365 -newkey rsa:1024 -sha1 -subj "/C=VN/ST=/L=HaNoi/CN=www.example.com"
     * -keyout ./src/test/resources/private_key.pem -out ./src/test/resources/rsa_cert.pem}
     * <p>
     * {@code openssl pkcs8 -in ./src/test/resources/private_key.pem -topk8 -nocrypt -out
     * ./src/test/resources/rsa_key.pk8}
     */
    @Before
    public void setUp() {
        String fileName = this.getClass().getClassLoader().getResource("rsa_key.pk8").toString();
        System.out.println(fileName);
        System.setProperty("z.oauth.sec.algo.rsa.private_key", fileName);
        this.signatureService = new RSASha1SignatureService(SecurityService.loadRsaPrivateKey());
    }

    @Test
    public void test_GetSignatureMethod() {
        SignatureMethod signatureMethod = this.signatureService.getSignatureMethod();
        Assert.assertEquals("RSA-SHA1", signatureMethod.toString());
        Assert.assertEquals("RSA-SHA1", signatureMethod.getName());
        Assert.assertEquals("SHA1withRSA", signatureMethod.getAlgorithm());
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_BaseStringNull() {
        this.signatureService.computeSignature(null, null, null);
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_BaseStringEmpty() {
        this.signatureService.computeSignature("", "", null);
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_PrivateKeyNull() {
        new RSASha1SignatureService(null);
    }

    @Test(expected = RuntimeException.class)
    public void test_GetSignature_FileError() throws Throwable {
        System.setProperty("z.oauth.sec.algo.rsa.private_key", "/tmp/xx");
        try {
            new RSASha1SignatureService(SecurityService.loadRsaPrivateKey());
        } catch (OAuthSecurityException ex) {
            throw ex.getCause();
        }
    }

    @Test(expected = InvalidKeySpecException.class)
    public void test_GetSignature_KeyNotValid() throws Throwable {
        String filePath = this.getClass().getClassLoader().getResource("none_private_key.txt").toString();
        System.setProperty("z.oauth.sec.algo.rsa.private_key", filePath);
        try {
            new RSASha1SignatureService(SecurityService.loadRsaPrivateKey());
        } catch (OAuthSecurityException ex) {
            throw ex.getCause();
        }
    }

    @Test
    public void test_GetSignature_Success() {
        Assert.assertEquals("IAbWzJW0ixW7sJd+Dck4ZDEUg+r2BzuNiFdfsr6egdXRjQoxn2nazwL" +
                            "+hvE0pqTqonIKF6LmgPa9DZg7Z4m9rZ72OR2Xp7Ymyr39rTAvBxOvyX/ACy7K0zJFzH" +
                            "+rICyNbrRB6Y9x3TsJkofA84VRWmkZ7U9QRxmMSC5QDCrVCDc=",
                            this.signatureService.computeSignature("abc", null, null));
    }

}
