package com.zero.oauth.client.type;

import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines OAuth 2.0 authorization grant type.
 *
 * @see OAuthVersion#V2
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public enum GrantType {
    /**
     *
     */
    AUTH_CODE("authorization_code", "code", Arrays.asList(FlowStep.AUTHORIZE, FlowStep.FETCH_TOKEN)),

    /**
     *
     */
    IMPLICIT("token", null, Arrays.asList(FlowStep.AUTHORIZE)),

    /**
     *
     */
    PASSWORD("password", null, Arrays.asList(FlowStep.FETCH_TOKEN)),

    /**
     *
     */
    CLIENT_CREDENTIALS("client_credentials", null, Arrays.asList(FlowStep.FETCH_TOKEN)),

    /**
     *
     */
    REFRESH_TOKEN("refresh_token", null, Arrays.asList(FlowStep.FETCH_TOKEN)),

    /**
     *
     */
    DEVICE_CODE("urn:ietf:params:oauth:grant-type:device_code", null, Arrays.asList(FlowStep.AUTHORIZE));

    private final String grantType;
    private final String responseType;
    private final List<FlowStep> steps;
}
