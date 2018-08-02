package com.zero.oauth.client.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @see <a href="https://oauth.net/core/1.0a/#encoding_parameters">Encode parameter</a>
 * @see <a href="https://tools.ietf.org/html/rfc3986#section-2.3">RFC3986#section-2.3</a>
 * @see <a href="https://tools.ietf.org/html/rfc6749#appendix-B">RFC6749#appendix-B</a>
 */
public class OAuthEnconderTest {

    @Test(expected = NullPointerException.class)
    public void test_encode_null() {
        OAuthEncoder.encode(null);
    }

    @Test(expected = NullPointerException.class)
    public void test_decode_null() {
        OAuthEncoder.decode(null);
    }

    @Test
    public void test_plus_sign() {
        System.out.println("Plus sign: \u002B");
        assertEquals("%2B", OAuthEncoder.encode("+"));
        assertEquals("+", OAuthEncoder.decode("%2B"));
    }

    @Test
    public void test_space_sign() {
        System.out.println("Space sign: '\u0020'");
        assertEquals("%20", OAuthEncoder.encode(" "));
        assertEquals(" ", OAuthEncoder.decode("%20"));
    }

    @Test
    public void test_percent_sign() {
        System.out.println("Percent sign: \u0025");
        assertEquals("%25", OAuthEncoder.encode("%"));
        assertEquals("%", OAuthEncoder.decode("%25"));
    }

    @Test
    public void test_ampersand_sign() {
        System.out.println("Ampersand sign: \u0026");
        assertEquals("%26", OAuthEncoder.encode("&"));
        assertEquals("&", OAuthEncoder.decode("%26"));
    }

    @Test
    public void test_pound_sign() {
        System.out.println("Pound sign: \u00A3");
        assertEquals("%C2%A3", OAuthEncoder.encode("\u00A3"));
        assertEquals("\u00A3", OAuthEncoder.decode("%C2%A3"));
    }

    @Test
    public void test_euro_sign() {
        System.out.println("Euro sign: \u20AC");
        assertEquals("%E2%82%AC", OAuthEncoder.encode("\u20AC"));
        assertEquals("\u20AC", OAuthEncoder.decode("%E2%82%AC"));
    }

    @Test
    public void test_tilde_sign() {
        System.out.println("Tilde sign: \u007E");
        assertEquals("~", OAuthEncoder.encode("~"));
        assertEquals("~", OAuthEncoder.decode("~"));
    }

    @Test
    public void test_hyphen_sign() {
        System.out.println("Hyphen sign: \u002D");
        assertEquals("-", OAuthEncoder.encode("-"));
        assertEquals("-", OAuthEncoder.decode("-"));
    }

    @Test
    public void test_underscore_sign() {
        System.out.println("Underscore sign: \u005F");
        assertEquals("_", OAuthEncoder.encode("_"));
        assertEquals("_", OAuthEncoder.decode("_"));
    }

    @Test
    public void test_dot_sign() {
        System.out.println("Dot sign: \u002E");
        assertEquals(".", OAuthEncoder.encode("."));
        assertEquals(".", OAuthEncoder.decode("."));
    }

    @Test
    public void test_equal_sign() {
        assertEquals("%3D", OAuthEncoder.encode("="));
        assertEquals("=", OAuthEncoder.decode("%3D"));
    }

    @Test
    public void test_decode_FormURL_plus_to_space() {
        assertEquals("this is", OAuthEncoder.decode("this+is"));
    }

    @Test
    public void test_decode_FormURL_equal_to_space() {
        assertEquals("this=that", OAuthEncoder.decode("this%3Dthat"));
    }

    @Test
    public void test_reservedCharacters_noEncode() {
        final String plain = "abcdefghijklmnopqrstuxyzwABCDEFGHIJKLMNOPQRSTUXYZW1234567890-._~";
        assertEquals(plain, OAuthEncoder.encode(plain));
        assertEquals(plain, OAuthEncoder.decode(plain));
    }

}
