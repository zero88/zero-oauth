package com.zero.oauth.client.core.properties.converter;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.zero.oauth.client.core.oauth1.OAuth1RequestProperties;
import com.zero.oauth.client.core.oauth1.OAuth1ResponseProp;
import com.zero.oauth.client.core.oauth1.OAuth1ResponseProperties;
import com.zero.oauth.client.core.oauth2.OAuth2RequestProperties;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.type.SignatureMethod;

public class HeaderConverterTest {

    private static final String CLIENT_ID = "0oaftyjk6bi1jgGFN0h7";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth/callback";
    private static final String REDIRECT_URI_ENCODE =
            "http%3A%2F%2Flocalhost%3A8080%2Foauth%2Fcallback";
    private static final String STATE = "m99oZsYX6wk5b1Sf";
    private static final String SCOPE = "channels:read team:read";
    private static final String SCOPE_ENCODE = "channels%3Aread%20team%3Aread";
    private static final String SIGNATURE = "wOJIO9A2W5mFwDgiDvZbTSMK/PY=";
    private static final String SIGNATURE_ENCODE = "wOJIO9A2W5mFwDgiDvZbTSMK%2FPY%3D";
    private static final String TIMESTAMP = "137131200";
    private static final String NONCE = "4572616e48616d6d65724c61686176";
    private static final String TOKEN = "hh5s93j4hdidpola";
    private static final String TOKEN_SECRET = "hdhd0244k9j7ao03";
    private static final String TOKEN_CONFIRMED = "true";

    @Test
    public void test_RequestProp_ParameterConvert_OAuth2() {
        OAuth2RequestProperties requestProperties =
                OAuth2RequestProperties.init(GrantType.AUTH_CODE);
        requestProperties.update("client_id", CLIENT_ID);
        requestProperties.update("redirect_uri", REDIRECT_URI);
        requestProperties.update("scope", SCOPE);
        requestProperties.update("state", STATE);
        String headers =
                new RequestHeaderConverter<>(requestProperties).serialize(FlowStep.AUTHORIZE);
        System.out.println("Serialize: " + headers);
        assertThat(Arrays.asList(headers.split("\\,")),
                   hasItems("response_type=\"code\"", "client_id=\"" + CLIENT_ID + "\"",
                            "state=\"" + STATE + "\"", "scope=\"" + SCOPE_ENCODE + "\"",
                            "redirect_uri=\"" + REDIRECT_URI_ENCODE + "\""));
    }

    @Test(expected = OAuthParameterException.class)
    public void test_RequestProp_ParameterConvert_MissingValue() {
        new RequestHeaderConverter<>(OAuth2RequestProperties.init(GrantType.AUTH_CODE))
                .serialize(FlowStep.AUTHORIZE);
    }

    @Test
    public void test_RequestProp_HeaderConvert_OAuth1() {
        OAuth1RequestProperties requestProperties = new OAuth1RequestProperties();
        requestProperties.update("oauth_consumer_key", CLIENT_ID);
        requestProperties.update("oauth_callback", REDIRECT_URI);
        requestProperties.update("oauth_signature_method", SignatureMethod.HMAC_SHA1);
        requestProperties.update("oauth_signature", SIGNATURE);
        requestProperties.update("oauth_timestamp", TIMESTAMP);
        requestProperties.update("oauth_nonce", NONCE);
        String headers =
                new RequestHeaderConverter<>(requestProperties).serialize(FlowStep.REQUEST);
        System.out.println("Serialize: " + headers);
        assertThat(Arrays.asList(headers.split(",")),
                   hasItems("oauth_consumer_key=\"" + CLIENT_ID + "\"",
                            "oauth_callback=\"" + REDIRECT_URI_ENCODE + "\"",
                            "oauth_signature_method=\"" + SignatureMethod.HMAC_SHA1 + "\"",
                            "oauth_signature=\"" + SIGNATURE_ENCODE + "\"",
                            "oauth_timestamp=\"" + TIMESTAMP + "\"",
                            "oauth_nonce=\"" + NONCE + "\""));
    }

    @Test
    public void test_ResponseProp_HeaderConvert_OAuth1() {
        String headers =
                "oauth_token=\"" + TOKEN + "\"," + "oauth_token_secret=\"" + TOKEN_SECRET + "\"," +
                "oauth_callback_confirmed=\"" + TOKEN_CONFIRMED + "\"";
        System.out.println("Deserialize: " + headers);
        PropertyStore<IPropertyModel> store =
                new RequestHeaderConverter<>(new OAuth1ResponseProperties())
                        .deserialize(headers, FlowStep.REQUEST);
        assertEquals(TOKEN, store.get(OAuth1ResponseProp.TOKEN.getName()).getValue());
        assertEquals(TOKEN_SECRET, store.get(OAuth1ResponseProp.TOKEN_SECRET.getName()).getValue());
        assertEquals(TOKEN_CONFIRMED,
                     store.get(OAuth1ResponseProp.CALLBACK_CONFIRMED.getName()).getValue());
    }

    @Test(expected = OAuthParameterException.class)
    public void test_ResponseProp_ParameterConvert_WrongSyntax() {
        String headers = "oauth_token==\"" + TOKEN + "\",";
        new RequestHeaderConverter<>(new OAuth1ResponseProperties())
                .deserialize(headers, FlowStep.REQUEST);
    }

}
