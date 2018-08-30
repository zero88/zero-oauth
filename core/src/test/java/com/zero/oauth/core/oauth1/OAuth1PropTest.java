package com.zero.oauth.core.oauth1;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.type.FlowStep;

public class OAuth1PropTest extends TestBase {

    private OAuth1RequestProperties requestProperties;
    private OAuth1ResponseProperties responseProperties;

    @Before
    public void init() {
        requestProperties = new OAuth1RequestProperties();
        responseProperties = new OAuth1ResponseProperties();
    }

    @Test
    public void test_Duplicate_Request_Property() {
        OAuth1RequestProperty duplicate = OAuth1RequestProperty.CONSUMER_KEY.duplicate();
        assertNotSame(OAuth1RequestProperty.CONSUMER_KEY, duplicate);
        assertEquals(OAuth1RequestProperty.CONSUMER_KEY, duplicate);
        assertEquals(duplicate.getMapping(), OAuth1RequestProperty.CONSUMER_KEY.getMapping());
    }

    @Test
    public void test_Request_RequestToken() {
        List<OAuth1RequestProperty> by = requestProperties.by(FlowStep.REQUEST);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("realm", "oauth_consumer_key", "oauth_signature_method", "oauth_signature",
                                        "oauth_timestamp", "oauth_nonce", "oauth_version", "oauth_callback"));
    }

    @Test
    public void test_Response_RequestToken() {
        List<OAuth1ResponseProperty> by = responseProperties.by(FlowStep.REQUEST);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("oauth_token", "oauth_token_secret", "oauth_callback_confirmed"));
    }

    @Test
    public void test_RequestProp_FilterBy_Authorize() {
        List<OAuth1RequestProperty> by = requestProperties.by(FlowStep.AUTHORIZE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("realm", "oauth_token"));
    }

    @Test
    public void test_ResponseProp_FilterBy_Authorize() {
        List<OAuth1ResponseProperty> by = responseProperties.by(FlowStep.AUTHORIZE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("oauth_token", "oauth_verifier"));
    }

    @Test
    public void test_RequestProp_FilterBy_ExchangeToken() {
        List<OAuth1RequestProperty> by = requestProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames,
                   hasItems("realm", "oauth_consumer_key", "oauth_token", "oauth_signature_method", "oauth_signature",
                            "oauth_timestamp", "oauth_nonce", "oauth_version", "oauth_verifier"));
    }

    @Test
    public void test_ResponseProp_FilterBy_ExchangeToken() {
        List<OAuth1ResponseProperty> by = responseProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames, hasItems("oauth_token", "oauth_token_secret"));
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<OAuth1RequestProperty> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> paramNames = by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(paramNames,
                   hasItems("realm", "oauth_consumer_key", "oauth_token", "oauth_signature_method", "oauth_signature",
                            "oauth_timestamp", "oauth_nonce", "oauth_version"));
    }

}
