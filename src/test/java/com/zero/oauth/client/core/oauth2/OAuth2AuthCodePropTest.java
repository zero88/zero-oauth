package com.zero.oauth.client.core.oauth2;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

public class OAuth2AuthCodePropTest {

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
    public void test_RequestProp_FilterBy_ExchangeToken() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("grant_type", "client_id", "client_secret", "redirect_uri", "code"));
    }

    @Test
    public void test_ResponseProp_FilterBy_ExchangeToken() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names,
                   hasItems("access_token", "token_type", "expires_in", "refresh_token", "scope"));
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
        assertTrue("Error repsonse must have `error_description` prop key",
                   errorProperties.has("error_description"));
        assertTrue("`error_description` prop key must be optional",
                   errorProperties.get("error_description").isOptional());
        assertTrue("Error repsonse must have `error_uri` prop key", errorProperties.has("error_uri"));
        assertTrue("`error_uri` prop key must be optional", errorProperties.get("error_uri").isOptional());
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("access_token"));
    }

}
