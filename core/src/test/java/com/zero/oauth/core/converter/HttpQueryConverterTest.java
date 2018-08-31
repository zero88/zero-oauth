package com.zero.oauth.core.converter;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthParameterException;
import com.zero.oauth.core.oauth1.OAuth1ResponseProperties;
import com.zero.oauth.core.oauth2.OAuth2RequestProperties;
import com.zero.oauth.core.oauth2.OAuth2ResponseProperties;
import com.zero.oauth.core.oauth2.OAuth2ResponseProperty;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.GrantType;

public class HttpQueryConverterTest extends TestBase {

    private static final String CLIENT_ID = "0oaftyjk6bi1jgGFN0h7";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth/callback";
    private static final String REDIRECT_URI_ENCODE = "http%3A%2F%2Flocalhost%3A8080%2Foauth%2Fcallback";
    private static final String STATE = "m99oZsYX6wk5b1Sf";
    private static final String SCOPE = "channels:read team:read";
    private static final String SCOPE_ENCODE = "channels%3Aread%20team%3Aread";
    private static final String ACCESS_TOKEN
        = "eyJraWQiOiIyRDN3Y0lycGQxYWpUdERwZ0NKTnFHU09odXNhQTVDUnEwRGd2ZGZXNXFVIiwiYWxnIjoiUlMyNTYifQ";
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
        String parameters = new HttpQueryConverter().serialize(requestProperties, FlowStep.AUTHORIZE);
        assertThat(Arrays.asList(parameters.split("\\&")),
                   hasItems("response_type=token", "client_id=" + CLIENT_ID, "state=" + STATE, "scope=" + SCOPE_ENCODE,
                            "redirect_uri=" + REDIRECT_URI_ENCODE));
    }

    @Test(expected = OAuthParameterException.class)
    public void test_RequestProp_ParameterConvert_MissingValue() {
        new HttpQueryConverter().serialize(requestProperties, FlowStep.AUTHORIZE);
    }

    @Test
    public void test_ResponseProp_ParameterConverter() {
        String queryPath = "access_token=" + ACCESS_TOKEN + "&" + "token_type=" + TOKEN_TYPE + "&" + "expires_in=" +
                           EXPIRES_IN + "&" + "scope=" + SCOPE_ENCODE + "&" + "state=" + STATE;
        OAuth2ResponseProperties store = new HttpQueryConverter().deserialize(responseProperties, queryPath,
                                                                              FlowStep.AUTHORIZE);
        assertEquals(ACCESS_TOKEN, store.get(OAuth2ResponseProperty.ACCESS_TOKEN.getName()).getValue());
        assertEquals(TOKEN_TYPE, store.get(OAuth2ResponseProperty.TOKEN_TYPE.getName()).getValue());
        assertEquals(EXPIRES_IN, Integer.parseInt(store.get(OAuth2ResponseProperty.EXPIRES_IN.getName()).getValue()
                                                       .toString()));
        assertEquals(SCOPE, store.get(OAuth2ResponseProperty.SCOPE.getName()).getValue());
        assertEquals(STATE, store.get(OAuth2ResponseProperty.AUTH_STATE.getName()).getValue());
    }

    @Test(expected = OAuthParameterException.class)
    public void test_ResponseProp_ParameterConvert_WrongSyntax() {
        String headers = "oauth_token==\"" + ACCESS_TOKEN + "\",";
        new HttpQueryConverter().deserialize(new OAuth1ResponseProperties(), headers, FlowStep.REQUEST);
    }

}
