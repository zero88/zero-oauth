package com.zero.oauth.client.core.oauth2;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.converter.JsonConverter;
import com.zero.oauth.client.core.properties.converter.RequestParamConverter;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

public class OAuth2AuthCodePropTest {

    private OAuth2RequestProperties requestProperties;
    private OAuth2ResponseProperties responseProperties;
    private OAuth2ResponseProperties errorProperties;
    private static final String CLIENT_ID = "91599da341f8";
    private static final String CLIENT_SECRET = "072d8702a86fe8b86bb4b670";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth/callback";
    private static final String REDIRECT_URI_ENCODE = "http%3A%2F%2Flocalhost%3A8080%2Foauth%2Fcallback";
    private static final String STATE = "ee0c8fd455ba";
    private static final String SCOPE = "channels:read team:read";
    private static final String SCOPE_ENCODE = "channels%3Aread+team%3Aread";
    private static final String TOKEN_CODE = "265759c709be";

    @Before
    public void init() {
        requestProperties = OAuth2RequestProperties.init(GrantType.AUTH_CODE);
        responseProperties = OAuth2ResponseProperties.init(GrantType.AUTH_CODE);
        errorProperties = OAuth2ResponseProperties.initError(GrantType.AUTH_CODE);
    }

    @Test(expected = OAuthParameterException.class)
    public void testRequest_FilterBy_Init() {
        requestProperties.by(FlowStep.INIT);
    }

    @Test
    public void test_RequestProp_FilterBy_Authorize() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.AUTHORIZE);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("client_id", "response_type", "redirect_uri", "scope", "state"));
    }

    @Test
    public void test_ResponseProp_FilterBy_Authorize() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.AUTHORIZE);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("token", "state"));
    }

    @Test
    public void test_RequestProp_FilterBy_AccessToken() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.ACCESS_TOKEN);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("grant_type", "client_id", "client_secret", "redirect_uri", "code"));
    }

    @Test
    public void test_ResponseProp_FilterBy_AccessToken() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.ACCESS_TOKEN);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("access_token", "token_type", "expires_in", "refresh_token", "scope"));
    }

    @Test
    public void test_RequestProp_ResponseType() {
        OAuth2RequestProp customValue = requestProperties.get(OAuth2RequestProp.RESPONSE_TYPE.getName());
        assertNotSame(OAuth2RequestProp.RESPONSE_TYPE, customValue);
        assertEquals("code", customValue.getValue());
    }

    @Test
    public void test_RequestProp_GrantType() {
        OAuth2RequestProp customValue2 = requestProperties.get(OAuth2RequestProp.GRANT_TYPE.getName());
        assertNotSame(OAuth2RequestProp.GRANT_TYPE, customValue2);
        assertEquals("authorization_code", customValue2.getValue());
    }

    @Test
    public void test_ResponseProp_Error() {
        assertTrue("Error repsonse must be marked as `error=True`", errorProperties.isError());
        assertTrue("Error repsonse must have `error` prop key", errorProperties.has("error"));
        assertTrue("`error` prop key must be required", errorProperties.get("error").isRequired());
        assertTrue("Error repsonse must have `error_description` prop key", errorProperties.has("error_description"));
        assertTrue("`error_description` prop key must be optional", errorProperties.get("error_description").isOptional());
        assertTrue("Error repsonse must have `error_uri` prop key", errorProperties.has("error_uri"));
        assertTrue("`error_uri` prop key must be optional", errorProperties.get("error_uri").isOptional());
    }

    @Test
    public void test_RequestProp_Convert_To_Parameters() {
        requestProperties.update("client_id", CLIENT_ID);
        requestProperties.update("redirect_uri", REDIRECT_URI);
        requestProperties.update("scope", SCOPE);
        requestProperties.update("state", STATE);
        String parameters = new RequestParamConverter().serialize(requestProperties, FlowStep.AUTHORIZE);
        assertThat(Arrays.asList(parameters.split("\\&")),
                   hasItems("response_type=code",
                            "client_id=" + CLIENT_ID,
                            "state=" + STATE,
                            "scope=" + SCOPE_ENCODE,
                            "redirect_uri=" + REDIRECT_URI_ENCODE));
    }

    @Test
    public void test_RequestProp_Convert_To_Json() throws JSONException {
        requestProperties.update("client_id", CLIENT_ID);
        requestProperties.update("redirect_uri", REDIRECT_URI);
        requestProperties.update("client_secret", CLIENT_SECRET);
        requestProperties.update("code", TOKEN_CODE);
        String body = new JsonConverter().serialize(requestProperties, FlowStep.ACCESS_TOKEN);
        String expected = "{\"code\": \"265759c709be\",\n" + "  \"grant_type\": \"authorization_code\",\n" +
                          "  \"client_secret\": \"072d8702a86fe8b86bb4b670\",\n" + "  \"redirect_uri\": \"http://localhost:8080/oauth/callback\",\n" +
                          "  \"client_id\": \"91599da341f8\"\n" + "}";
        JSONAssert.assertEquals(expected, body, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test(expected = OAuthParameterException.class)
    public void test_RequestProp_Convert_To_Parameters_MissingValue() {
        String parameters = new RequestParamConverter().serialize(requestProperties, FlowStep.AUTHORIZE);
        assertEquals("response_type=code", parameters);
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("access_token"));
    }
}
