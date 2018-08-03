package com.zero.oauth.client.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.client.exceptions.OAuthSignatureException;
import com.zero.oauth.client.type.SignatureMethod;

public class HmacSha1SignatureServiceTest {

    private SignatureService signatureService;

    @Before
    public void setUp() {
        this.signatureService = new HMACSha1SignatureService();
    }

    @Test
    public void test_GetSignatureMethod() {
        SignatureMethod signatureMethod = this.signatureService.getSignatureMethod();
        Assert.assertEquals("HMAC-SHA1", signatureMethod.toString());
        Assert.assertEquals("HMAC-SHA1", signatureMethod.getName());
        Assert.assertEquals("HmacSHA1", signatureMethod.getAlgorithm());
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_BaseNull() {
        this.signatureService.computeSignature(null, "", null);
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_BaseEmpty() {
        this.signatureService.computeSignature("", "", null);
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_SecretNull() {
        this.signatureService.computeSignature("abc", null, null);
    }

    @Test(expected = OAuthSignatureException.class)
    public void test_GetSignature_SecretEmpty() {
        this.signatureService.computeSignature("abc", "", null);
    }

    @Test
    public void test_GetSignature_TokenEmpty_Success() {
        Assert.assertEquals("gErq2wPGjgRNw+AZlMYc7B0iNhQ=",
                            this.signatureService.computeSignature("kakacljt", "abc", null));
    }

    @Test
    public void test_GetSignature_Success() {
        Assert.assertEquals("C93Smk6u0p9xVmM+gcUbcnbIpOU=",
                            this.signatureService.computeSignature("kakacljt", "abc", "xyz"));
    }

}
