package com.zero.oauth.client;

import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.utils.Strings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
abstract class AbstractOAuthApi implements OAuthApi {

    private final String authorizeUrl;
    private final String accessTokenUrl;
    private final IRequestProperties requestProperties;
    private String apiBaseUrl;

    protected abstract String getClientIdName();

    protected abstract String getClientSecretName();

    final void updateClientInfo(String clientId, String clientSecret) {
        this.requestProperties.update(getClientIdName(), Strings.requireNotBlank(clientId));
        this.requestProperties.update(getClientSecretName(), Strings.requireNotBlank(clientSecret));
    }

    @Override
    public final String getClientId() {
        return this.requestProperties.get(getClientIdName()).getValue().toString();
    }

    @Override
    public final String getClientSecret() {
        return this.requestProperties.get(getClientSecretName()).getValue().toString();
    }

    @Override
    public final OAuthApi apiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        return this;
    }

}
