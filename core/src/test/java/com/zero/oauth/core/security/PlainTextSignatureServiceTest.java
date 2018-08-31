package com.zero.oauth.core.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthSignatureException;
import com.zero.oauth.core.type.SignatureMethod;

public class PlainTextSignatureServiceTest extends TestBase {

    private SignatureService signatureService;

    @Before
    public void setUp() {
        this.signatureService = new PlainTextSignatureService();
    }

    @Test
    public void test_GetSignatureMethod() {
        SignatureMethod signatureMethod = this.signatureService.getSignatureMethod();
        Assert.assertEquals("PLAINTEXT", signatureMethod.toString());
        Assert.assertEquals("PLAINTEXT", signatureMethod.getName());
        Assert.assertNull(signatureMethod.getAlgorithm());
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_SecretNull() {
        this.signatureService.computeSignature("", "", null);
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_SecretEmpty() {
        this.signatureService.computeSignature("", "", null);
    }

    @Test
    public void test_GetSignature_TokenEmpty_Success() {
        Assert.assertEquals("abc&", this.signatureService.computeSignature(null, "abc", null));
    }

    @Test
    public void test_GetSignature_Success() {
        Assert.assertEquals("abc&xyz", this.signatureService.computeSignature(null, "abc", "xyz"));
    }

}
