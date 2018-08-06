package com.zero.oauth.client.core.properties.converter;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.zero.oauth.client.core.oauth2.OAuth2RequestProperties;
import com.zero.oauth.client.core.oauth2.OAuth2ResponseProperties;
import com.zero.oauth.client.core.oauth2.OAuth2ResponseProperty;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

public class JsonConverterTest {

    private static final String CLIENT_ID = "91599da341f8";
    private static final String CLIENT_SECRET = "072d8702a86fe8b86bb4b670";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth/callback";
    private static final String TOKEN_CODE = "aR8o7Jk2vYydVrT9sqd1";
    private static final String STATE = "Lsbf6XKxFvc-7BL9";
    private static final String ACCESS_TOKEN =
        "eyJraWQiOiIyRDN3Y0lycGQxYWpUdERwZ0NKTnFHU09odXNhQTVDUnEwRGd2ZGZXNXFVI";
    private static final String TOKEN_TYPE = "Bearer";
    private static final int EXPIRES_IN = 3600;
    private static final String SCOPE = "channels:read team:read";
    private static final String REFRESH_TOKEN = "GcqENB_wXPxr7IYVNVvdG7S_v4_NI6pKXhclV-tSsNU";
    private OAuth2RequestProperties requestProperties;
    private OAuth2ResponseProperties responseProperties;

    @Before
    public void init() {
        requestProperties = OAuth2RequestProperties.init(GrantType.AUTH_CODE);
        responseProperties = OAuth2ResponseProperties.init(GrantType.AUTH_CODE);
    }

    @Test
    public void test_RequestProp_ExchangeToken_JsonConverter() throws JSONException {
        requestProperties.update("client_id", CLIENT_ID);
        requestProperties.update("redirect_uri", REDIRECT_URI);
        requestProperties.update("client_secret", CLIENT_SECRET);
        requestProperties.update("code", TOKEN_CODE);
        String body = new JsonConverter<>(requestProperties).serialize(FlowStep.EXCHANGE_TOKEN);
        String expected =
            "{\"code\": \"" + TOKEN_CODE + "\",\n" + "  \"grant_type\": \"authorization_code\",\n" +
            "  \"client_secret\": \"" + CLIENT_SECRET + "\",\n" + "  \"redirect_uri\": \"" + REDIRECT_URI +
            "\",\n" + "  \"client_id\": \"" + CLIENT_ID + "\"}";
        JSONAssert.assertEquals(expected, body, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected = OAuthParameterException.class)
    public void test_RequestProp_JsonConverter_MissingValue() {
        String serialize = new JsonConverter<>(requestProperties).serialize(FlowStep.EXCHANGE_TOKEN);
        System.out.println("JSON: " + serialize);
    }

    @Test
    public void test_ResponseProp_ExchangeToken_JsonConverter() {
        String body =
            "{\"access_token\": \"" + ACCESS_TOKEN + "\",\n" + "  \"token_type\": \"" + TOKEN_TYPE + "\",\n" +
            "  \"expires_in\": " + EXPIRES_IN + ",\n" + "  \"refresh_token\": \"" + REFRESH_TOKEN + "\",\n" +
            "  \"scope\": \"" + SCOPE + "\"}";
        IPropertyStore<IPropertyModel> deserialize =
            new JsonConverter<>(responseProperties).deserialize(body, FlowStep.EXCHANGE_TOKEN);
        assertEquals(ACCESS_TOKEN, deserialize.get(OAuth2ResponseProperty.ACCESS_TOKEN.getName()).getValue());
        assertEquals(TOKEN_TYPE, deserialize.get(OAuth2ResponseProperty.TOKEN_TYPE.getName()).getValue());
        assertEquals(EXPIRES_IN,
                     ((Double) deserialize.get(OAuth2ResponseProperty.EXPIRES_IN.getName()).getValue())
                         .intValue());
        assertEquals(REFRESH_TOKEN,
                     deserialize.get(OAuth2ResponseProperty.REFRESH_TOKEN.getName()).getValue());
        assertEquals(SCOPE, deserialize.get(OAuth2ResponseProperty.SCOPE.getName()).getValue());
    }

}
