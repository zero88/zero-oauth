package com.zero.oauth.core.oauth2;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthParameterException;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.GrantType;

public class OAuth2DeviceCodePropTest extends TestBase {

    private OAuth2RequestProperties requestProperties;
    private OAuth2ResponseProperties responseProperties;

    @Before
    public void init() {
        requestProperties = OAuth2RequestProperties.init(GrantType.DEVICE_CODE);
        responseProperties = OAuth2ResponseProperties.init(GrantType.DEVICE_CODE);
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
        assertThat(paramNames, hasItems("response_type", "client_id", "scope"));
    }

    @Test
    public void testResponseParams_FilterBy_ExchangeToken() {
        List<OAuth2ResponseProperty> by = responseProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("device_code", "user_code", "verification_url", "expires_in", "interval"));
    }

    @Test
    public void testRequestParams_FilterBy_Polling() {
        List<OAuth2RequestProperty> by = requestProperties.by(FlowStep.POLLING);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("grant_type", "client_id", "client_secret", "code"));
    }

    @Test
    public void testResponseParams_FilterBy_Polling() {
        List<OAuth2ResponseProperty> by = responseProperties.by(FlowStep.POLLING);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("access_token", "refresh_token", "expires_in", "token_type"));
    }

    @Test
    public void testRequestParams_DefaultValue() {
        OAuth2RequestProperty prop1 = requestProperties.get(OAuth2RequestProperty.RESPONSE_TYPE.getName());
        assertEquals("device_code", prop1.getValue());
        OAuth2RequestProperty prop2 = requestProperties.get(OAuth2RequestProperty.GRANT_TYPE.getName());
        assertEquals("urn:ietf:params:oauth:grant-type:device_code", prop2.getValue());
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<OAuth2RequestProperty> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("access_token"));
    }

}
