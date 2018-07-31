package com.zero.oauth.client.core.oauth1;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.type.FlowStep;

public class OAuth1PropTest {

    private OAuth1RequestProperties requestProperties;
    private OAuth1ResponseProperties responseProperties;

    @Before
    public void init() {
        requestProperties = new OAuth1RequestProperties();
        responseProperties = new OAuth1ResponseProperties();
    }

    @Test
    public void testRequest_RequestToken() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.REQUEST);
        List<String> param_names =
                by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("realm", "oauth_consumer_key", "oauth_signature_method",
                                         "oauth_signature", "oauth_timestamp", "oauth_nonce",
                                         "oauth_version", "oauth_callback"));
    }

    @Test
    public void testResponse_RequestToken() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.REQUEST);
        List<String> param_names =
                by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names,
                   hasItems("oauth_token", "oauth_token_secret", "oauth_callback_confirmed"));
    }

    @Test
    public void test_RequestProp_FilterBy_Authorize() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.AUTHORIZE);
        List<String> param_names =
                by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("realm", "oauth_token"));
    }

    @Test
    public void test_ResponseProp_FilterBy_Authorize() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.AUTHORIZE);
        List<String> param_names =
                by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("oauth_token", "oauth_verifier"));
    }

    @Test
    public void test_RequestProp_FilterBy_ExchangeToken() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> param_names =
                by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names,
                   hasItems("realm", "oauth_consumer_key", "oauth_token", "oauth_signature_method",
                            "oauth_signature", "oauth_timestamp", "oauth_nonce", "oauth_version",
                            "oauth_verifier"));
    }

    @Test
    public void test_ResponseProp_FilterBy_ExchangeToken() {
        List<IPropertyModel> by = responseProperties.by(FlowStep.EXCHANGE_TOKEN);
        List<String> param_names =
                by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names, hasItems("oauth_token", "oauth_token_secret"));
    }

    @Test
    public void test_RequestProp_FilterBy_AccessResource() {
        List<IPropertyModel> by = requestProperties.by(FlowStep.ACCESS_RESOURCE);
        List<String> param_names =
                by.stream().map(IPropertyModel::getName).collect(Collectors.toList());
        assertThat(param_names,
                   hasItems("realm", "oauth_consumer_key", "oauth_token", "oauth_signature_method",
                            "oauth_signature", "oauth_timestamp", "oauth_nonce", "oauth_version"));
    }

}
