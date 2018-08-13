package com.zero.oauth.client.core.oauth2;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

public class OAuth2PasswordPropTest {

    private OAuth2RequestProperties requestProperties;
    private OAuth2ResponseProperties responseProperties;

    @Before
    public void init() {
        requestProperties = OAuth2RequestProperties.init(GrantType.PASSWORD);
        responseProperties = OAuth2ResponseProperties.init(GrantType.PASSWORD);
    }

    @Test(expected = OAuthParameterException.class)
    public void testRequestParams_RequestToken() {
        requestProperties.by(FlowStep.REQUEST);
    }

    @Test(expected = OAuthParameterException.class)
    public void testRequestParams_FilterBy_Authorize() {
        requestProperties.by(FlowStep.AUTHORIZE);
    }

    @Test
    public void testRequestParams_FilterBy_ExchangeToken() {
        List<OAuth2RequestProperty> by = requestProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames,
                   hasItems("grant_type", "client_id", "client_secret", "redirect_uri", "username", "password"));
    }

    @Test
    public void testResponseParams_FilterBy_ExchangeToken() {
        List<OAuth2ResponseProperty> by = responseProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("access_token", "token_type", "expires_in", "refresh_token", "scope"));
    }

    @Test
    public void testRequestParams_DefaultValue() {
        OAuth2RequestProperty prop = requestProperties.get(OAuth2RequestProperty.GRANT_TYPE.getName());
        assertEquals("password", prop.getValue());
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<OAuth2RequestProperty> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("access_token"));
    }

}
