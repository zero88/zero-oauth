package com.zero.oauth.client.utils;

import org.junit.Assert;
import org.junit.Test;

import com.zero.oauth.client.exceptions.OAuthUrlException;

public class UrlsTest {

    private static final String DEFAULT_URL = "http://localhost:8080/api";

    @Test(expected = IllegalArgumentException.class)
    public void test_optimizeUrl_Blank() {
        Urls.optimizeUrl(null, null);
    }

    @Test(expected = OAuthUrlException.class)
    public void test_optimizeUrl_InvalidBaseFormat() {
        Urls.optimizeUrl("htt://abc", null);
    }

    @Test(expected = OAuthUrlException.class)
    public void test_optimizeUrl_InvalidPathFormat_BaseIsBlank() {
        Urls.optimizeUrl(null, "abc");
    }

    @Test
    public void test_optimizeUrl_NullPath() {
        Assert.assertEquals(DEFAULT_URL, Urls.optimizeUrl(DEFAULT_URL, null));
    }

    @Test
    public void test_optimizeUrl_BlankPath() {
        Assert.assertEquals(DEFAULT_URL, Urls.optimizeUrl(DEFAULT_URL, ""));
    }

    @Test
    public void test_optimizeUrl_FullPath() {
        Assert.assertEquals(DEFAULT_URL + "/auth", Urls.optimizeUrl(DEFAULT_URL, "/auth"));
    }

    @Test(expected = OAuthUrlException.class)
    public void test_optimizeUrl_PathWithSpace() {
        Urls.optimizeUrl(DEFAULT_URL, "auth/abc xyz");
    }

    @Test
    public void test_optimizeUrl_MessyPath1() {
        Assert.assertEquals(DEFAULT_URL + "/auth/t1", Urls.optimizeUrl(DEFAULT_URL, "//auth///t1"));
    }

    @Test
    public void test_optimizeUrl_MessyPath2() {
        Assert.assertEquals(DEFAULT_URL + "/auth/t2/", Urls.optimizeUrl(DEFAULT_URL, "//auth///t2///"));
    }

    @Test(expected = OAuthUrlException.class)
    public void test_optimizeUrl_PathWithParameter() {
        Assert.assertEquals(DEFAULT_URL + "/auth?name=x&age=20",
                            Urls.optimizeUrl(DEFAULT_URL, "auth?name=x&age=20"));
    }

    @Test(expected = OAuthUrlException.class)
    public void test_optimizeUrl_PathWithEncodeParameter() {
        Assert.assertEquals(DEFAULT_URL + "/auth?name=x%2By&age=20",
                            Urls.optimizeUrl(DEFAULT_URL, "auth?name=x+y&age=20"));
    }

    @Test
    public void test_optimizeUrl_PathIsUrl() {
        Assert.assertEquals("https://localhost/api", Urls.optimizeUrl(DEFAULT_URL, "https://localhost/api"));
    }

    @Test
    public void test_validUrl_LocalWithoutPort() {
        Assert.assertTrue(Urls.validateUrl("https://localhost"));
    }

    @Test
    public void test_validUrl_LocalWithPort() {
        Assert.assertTrue(Urls.validateUrl("https://localhost:80/"));
    }

    @Test
    public void test_validUrl_LocalWithTopLabel() {
        Assert.assertTrue(Urls.validateUrl("https://localhost.com"));
    }

    @Test
    public void test_validUrl_LocalWithPortAndTopLabel() {
        Assert.assertTrue(Urls.validateUrl("https://localhost.com:9090/"));
    }

    @Test
    public void test_validUrl_Remote() {
        Assert.assertTrue(Urls.validateUrl("https://github.com/google//blob/master/UserGuide.md"));
    }

    @Test
    public void test_validUrl_Remote_WithParameter() {
        Assert.assertFalse(Urls.validateUrl("https://github.org/wiki/GRUB_2?rd=Grub2"));
    }

    @Test
    public void test_validUrl_Remote_WithSpecialChar() {
        Assert.assertTrue(Urls.validateUrl("https://jenkins.org/blue/organizations/jenkins/zos%2Foauth-apis" +
                                           "-client/,();$!a+bc=d:@&!*xy/"));
    }

}
