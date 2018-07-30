package com.zero.oauth.client.core.properties.converter;

import com.zero.oauth.client.core.oauth2.OAuth2RequestProperties;
import com.zero.oauth.client.core.oauth2.OAuth2ResponseProp;
import com.zero.oauth.client.core.oauth2.OAuth2ResponseProperties;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ParameterConverterTest {

    private static final String CLIENT_ID = "0oaftyjk6bi1jgGFN0h7";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth/callback";
    private static final String REDIRECT_URI_ENCODE = "http%3A%2F%2Flocalhost%3A8080%2Foauth%2Fcallback";
    private static final String STATE = "m99oZsYX6wk5b1Sf";
    private static final String SCOPE = "channels:read team:read";
    private static final String SCOPE_ENCODE = "channels%3Aread%20team%3Aread";
    private static final String ACCESS_TOKEN = "eyJraWQiOiIyRDN3Y0lycGQxYWpUdERwZ0NKTnFHU09odXNhQTVDUnEwRGd2ZGZXNXFVIiwiYWxnIjoiUlMyNTYifQ";
    private static final String TOKEN_TYPE = "Bearer";
    private static final int EXPIRES_IN = 3600;
    private OAuth2RequestProperties requestProperties;
    private OAuth2ResponseProperties responseProperties;

    @Before
    public void init() {
        requestProperties = OAuth2RequestProperties.init(GrantType.IMPLICIT);
        responseProperties = OAuth2ResponseProperties.init(GrantType.IMPLICIT);
    }

    @Test
    public void test_RequestProp_ParameterConverter() {
        requestProperties.update("client_id", CLIENT_ID);
        requestProperties.update("redirect_uri", REDIRECT_URI);
        requestProperties.update("scope", SCOPE);
        requestProperties.update("state", STATE);
        String parameters = new RequestParamConverter<>(requestProperties).serialize(FlowStep.AUTHORIZE);
        assertThat(Arrays.asList(parameters.split("\\&")),
                hasItems("response_type=token",
                        "client_id=" + CLIENT_ID,
                        "state=" + STATE,
                        "scope=" + SCOPE_ENCODE,
                        "redirect_uri=" + REDIRECT_URI_ENCODE));
    }

    @Test(expected = OAuthParameterException.class)
    public void test_RequestProp_ParameterConvert_MissingValue() {
        new RequestParamConverter<>(requestProperties).serialize(FlowStep.AUTHORIZE);
    }

    @Test
    public void test_ResponseProp_ParameterConverter() {
        String queryPath = "access_token=" + ACCESS_TOKEN + "&" + "token_type=" + TOKEN_TYPE + "&" + "expires_in=" + EXPIRES_IN + "&" + "scope=" + SCOPE_ENCODE + "&" + "state=" + STATE;
        PropertyStore<IPropertyModel> properties = new RequestParamConverter<>(responseProperties).deserialize(queryPath, FlowStep.AUTHORIZE);
        assertEquals(ACCESS_TOKEN, properties.get(OAuth2ResponseProp.ACCESS_TOKEN.getName()).getValue());
        assertEquals(TOKEN_TYPE, properties.get(OAuth2ResponseProp.TOKEN_TYPE.getName()).getValue());
        assertEquals(EXPIRES_IN, Integer.parseInt(properties.get(OAuth2ResponseProp.EXPIRES_IN.getName()).getValue().toString()));
        assertEquals(SCOPE, properties.get(OAuth2ResponseProp.SCOPE.getName()).getValue());
        assertEquals(STATE, properties.get(OAuth2ResponseProp.AUTH_STATE.getName()).getValue());
    }
}
