package com.zero.oauth.core.oauth2;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthParameterException;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.GrantType;

public class OAuth2AuthCodePropTest extends TestBase {

    private OAuth2RequestProperties requestProperties;
    private OAuth2ResponseProperties responseProperties;
    private OAuth2ResponseProperties errorProperties;

    @Before
    public void init() {
        requestProperties = OAuth2RequestProperties.init(GrantType.AUTH_CODE);
        responseProperties = OAuth2ResponseProperties.init(GrantType.AUTH_CODE);
        errorProperties = OAuth2ResponseProperties.initError(GrantType.AUTH_CODE);
    }

    @Test(expected = OAuthParameterException.class)
    public void test_Request_RequestToken() {
        requestProperties.by(FlowStep.REQUEST);
    }

    @Test
    public void test_RequestProp_FilterBy_Authorize() {
        List<OAuth2RequestProperty> by = requestProperties.by(FlowStep.AUTHORIZE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("client_id", "response_type", "redirect_uri", "scope", "state"));
    }

    @Test
    public void test_ResponseProp_FilterBy_Authorize() {
        List<OAuth2ResponseProperty> by = responseProperties.by(FlowStep.AUTHORIZE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("code", "state"));
    }

    @Test
    public void test_RequestProp_FilterBy_ExchangeToken() {
        List<OAuth2RequestProperty> by = requestProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("grant_type", "client_id", "client_secret", "redirect_uri", "code"));
    }

    @Test
    public void test_ResponseProp_FilterBy_ExchangeToken() {
        List<OAuth2ResponseProperty> by = responseProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("access_token", "token_type", "expires_in", "refresh_token", "scope"));
    }

    @Test
    public void test_RequestProp_DefaultValue() {
        assertEquals("authorization_code",
                     requestProperties.get(OAuth2RequestProperty.GRANT_TYPE.getName()).getValue());
        assertEquals("code", requestProperties.get(OAuth2RequestProperty.RESPONSE_TYPE.getName()).getValue());
    }

    @Test
    public void test_RequestProp_DuplicateValue() {
        OAuth2RequestProperty property = OAuth2RequestProperty.RESPONSE_TYPE.duplicate("xyz");
        assertNotSame(OAuth2RequestProperty.RESPONSE_TYPE, property);
        assertEquals("xyz", property.getValue());
        assertEquals(OAuth2RequestProperty.RESPONSE_TYPE.getMapping(), property.getMapping());
    }

    @Test
    public void test_Response() {
        assertTrue(errorProperties.isError());
        assertFalse(responseProperties.isError());
        assertEquals(0, responseProperties.errors().size());
    }

    @Test
    public void test_ResponseProp_Error() {
        assertTrue("Error response must be marked as `error=True`", errorProperties.isError());
        assertThat(errorProperties.errors().stream().map(IPropertyModel::getName).collect(Collectors.toList()),
                   hasItems("error", "error_description", "error_uri"));
        assertTrue("`error` prop key must be required", errorProperties.get("error").isRequired());
        assertTrue("`error_description` prop key must be optional",
                   errorProperties.get("error_description").isOptional());
        assertTrue("`error_uri` prop key must be optional", errorProperties.get("error_uri").isOptional());
    }

    @Test
    public void test_ResponseProp_Error_Duplicate() {
        OAuth2ResponseProperty error = errorProperties.get("error").duplicate("invalid_request");
        assertNotSame(OAuth2ResponseProperty.ERROR_CODE, error);
        assertTrue(error.isError());
        assertEquals("invalid_request", error.getValue());
    }

    @Test
    public void test_ResponseProp_Duplicate() {
        OAuth2ResponseProperty responseProperty = responseProperties.by(FlowStep.EXCHANGE_TOKEN, "access_token")
                                                                    .duplicate("xxx");
        assertNotSame(OAuth2ResponseProperty.ACCESS_TOKEN, responseProperty);
        assertFalse(responseProperty.isError());
        assertEquals("xxx", responseProperty.getValue());
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<OAuth2RequestProperty> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("access_token"));
    }

    @Test
    public void test_RequestProp_RandomStateValue() {
        OAuth2RequestProperty state = OAuth2RequestProperty.STATE.duplicate();
        assertTrue(state.isComputeValue());
        assertNull(state.getValue());
        Object value1 = state.serialize();
        assertNotNull(value1);
        assertEquals(value1, state.serialize());
    }

    @Test
    public void test_RequestProp_ManualStateValue() {
        OAuth2RequestProperty state = OAuth2RequestProperty.STATE.duplicate();
        state.setValue("xyz");
        assertEquals("xyz", state.getValue());
        assertEquals("xyz", state.serialize());
    }

}
