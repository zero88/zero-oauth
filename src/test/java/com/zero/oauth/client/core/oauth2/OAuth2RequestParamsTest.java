package com.zero.oauth.client.core.oauth2;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.zero.oauth.client.core.PropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

public class OAuth2RequestParamsTest {

    @Test
    public void testParams_FilterBy_AuthGrant_AuthStep() {
        List<PropertyModel> by = new OAuth2RequestParams().by(GrantType.AUTH_CODE, FlowStep.AUTHORIZE);
        List<String> param_names = by.stream().map(PropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("client_id", "response_type", "redirect_uri", "scope", "state"));
    }

    @Test
    public void testParams_FilterBy_AuthGrant_FetchStep() {
        List<PropertyModel> by = new OAuth2RequestParams().by(GrantType.AUTH_CODE, FlowStep.FETCH_TOKEN);
        List<String> param_names = by.stream().map(PropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("grant_type", "client_id", "client_secret", "redirect_uri", "code"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParams_FilterBy_InitStep() {
        new OAuth2RequestParams().by(GrantType.AUTH_CODE, FlowStep.INIT);
    }

    @Test
    public void testParams_AuthGrant() {
        OAuth2RequestParams params = OAuth2RequestParams.init(GrantType.AUTH_CODE);
        OAuth2RequestParam customValue = params.getProp(OAuth2RequestParam.RESPONSE_TYPE.getName());
        assertNotSame(OAuth2RequestParam.RESPONSE_TYPE, customValue);
        assertEquals("code", customValue.getValue());
        OAuth2RequestParam customValue2 = params.getProp(OAuth2RequestParam.GRANT_TYPE.getName());
        assertNotSame(OAuth2RequestParam.GRANT_TYPE, customValue2);
        assertEquals("authorization_code", customValue2.getValue());
    }
}
