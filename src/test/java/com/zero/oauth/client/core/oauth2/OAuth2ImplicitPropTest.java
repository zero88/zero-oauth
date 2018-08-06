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

public class OAuth2ImplicitPropTest {

    private OAuth2RequestProperties requestProperties;
    private OAuth2ResponseProperties responseProperties;

    @Before
    public void init() {
        requestProperties = OAuth2RequestProperties.init(GrantType.IMPLICIT);
        responseProperties = OAuth2ResponseProperties.init(GrantType.IMPLICIT);
    }

    @Test(expected = OAuthParameterException.class)
    public void testRequestParams_RequestToken() {
        requestProperties.by(FlowStep.REQUEST);
    }

    @Test
    public void testRequestParams_FilterBy_Authorize() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.AUTHORIZE);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("response_type", "client_id", "redirect_uri", "scope", "state"));
    }

    @Test
    public void testResponseParams_FilterBy_ExchangeToken() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.AUTHORIZE);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("access_token", "token_type", "expires_in", "scope"));
    }

    @Test(expected = OAuthParameterException.class)
    public void testRequestParams_FilterBy_ExchangeToken() {
        requestProperties.by(FlowStep.EXCHANGE_TOKEN);
    }

    @Test
    public void testRequestParams_DefaultValue() {
        OAuth2RequestProperty property = requestProperties.get(OAuth2RequestProperty.RESPONSE_TYPE.getName());
        assertEquals("token", property.getValue());
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("access_token"));
    }

}
