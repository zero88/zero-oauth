package com.zero.oauth.client.type;

import java.util.Arrays;
import java.util.List;

import com.zero.oauth.client.utils.Strings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines OAuth 2.0 authorization grant type.
 *
 * @see OAuthVersion#V2
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum GrantType {
    /**
     *
     */
    AUTH_CODE("code", "authorization_code", Arrays.asList(FlowStep.AUTHORIZE,
                                                          FlowStep.ACCESS_TOKEN,
                                                          FlowStep.REVOKE_TOKEN,
                                                          FlowStep.ACCESS_RESOURCE)),

    /**
     *
     */
    IMPLICIT("token", null, Arrays.asList(FlowStep.AUTHORIZE, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE)),
    /**
     *
     */
    IMPLICIT_DEPRECATED("token", "authorization_code", Arrays.asList(FlowStep.AUTHORIZE,
                                                                     FlowStep.ACCESS_TOKEN,
                                                                     FlowStep.REVOKE_TOKEN,
                                                                     FlowStep.ACCESS_RESOURCE)),

    /**
     *
     */
    PASSWORD(null, "password", Arrays.asList(FlowStep.ACCESS_TOKEN, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE)),

    /**
     *
     */
    CLIENT_CREDENTIALS(null, "client_credentials", Arrays.asList(FlowStep.ACCESS_TOKEN, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE)),

    /**
     *
     */
    REFRESH_TOKEN(null, "refresh_token", Arrays.asList(FlowStep.ACCESS_TOKEN, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE)),

    /**
     * <a href="https://oauth.net/2/grant-types/device-code/">
     */
    DEVICE_CODE("device_code", "urn:ietf:params:oauth:grant-type:device_code", Arrays.asList(FlowStep.ACCESS_TOKEN,
                                                                                             FlowStep.POLLING,
                                                                                             FlowStep.REVOKE_TOKEN,
                                                                                             FlowStep.ACCESS_RESOURCE));

    private final String responseType;
    private final String grantType;
    /**
     * Available steps
     */
    private final List<FlowStep> steps;

    @Override
    public String toString() {
        return Strings.isNotBlank(grantType) ? grantType : responseType;
    }
}
