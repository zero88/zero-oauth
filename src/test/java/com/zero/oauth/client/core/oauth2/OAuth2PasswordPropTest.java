package com.zero.oauth.client.core.oauth2;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.client.core.IPropertyModel;
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

    @Test(expected = IllegalArgumentException.class)
    public void testRequestParams_FilterBy_Init() {
        requestProperties.by(FlowStep.INIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequestParams_FilterBy_Authorize() {
        requestProperties.by(FlowStep.AUTHORIZE);
    }

    @Test
    public void testRequestParams_FilterBy_AccessToken() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.ACCESS_TOKEN);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names,
                   hasItems("grant_type", "client_id", "client_secret", "redirect_uri", "username", "password"));
    }

    @Test
    public void testResponseParams_FilterBy_AccessToken() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.ACCESS_TOKEN);
        List<String> param_names = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("access_token", "token_type", "expires_in", "refresh_token", "scope"));
    }

    @Test
    public void testRequestParams_Implicit_Value() {
        OAuth2RequestProperties params = OAuth2RequestProperties.init(GrantType.PASSWORD);
        OAuth2RequestProp customValue2 = params.getProp(OAuth2RequestProp.GRANT_TYPE.getName());
        assertNotSame(OAuth2RequestProp.GRANT_TYPE, customValue2);
        assertEquals("password", customValue2.getValue());
    }
}
