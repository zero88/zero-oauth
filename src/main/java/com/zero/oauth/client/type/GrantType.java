package com.zero.oauth.client.type;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

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
                                                          FlowStep.REVOKE_TOKEN)),

    /**
     *
     */
    IMPLICIT("token", null, Arrays.asList(FlowStep.AUTHORIZE, FlowStep.REVOKE_TOKEN)),
    /**
     *
     */
    IMPLICIT_DEPRECATED("token", "authorization_code", Arrays.asList(FlowStep.AUTHORIZE,
                                                                     FlowStep.ACCESS_TOKEN,
                                                                     FlowStep.REVOKE_TOKEN)),

    /**
     *
     */
    PASSWORD(null, "password", Arrays.asList(FlowStep.ACCESS_TOKEN, FlowStep.REVOKE_TOKEN)),

    /**
     *
     */
    CLIENT_CREDENTIALS(null, "client_credentials", Arrays.asList(FlowStep.ACCESS_TOKEN, FlowStep.REVOKE_TOKEN)),

    /**
     *
     */
    REFRESH_TOKEN(null, "refresh_token", Arrays.asList(FlowStep.ACCESS_TOKEN, FlowStep.REVOKE_TOKEN)),

    /**
     * <a href="https://oauth.net/2/grant-types/device-code/">
     */
    DEVICE_CODE("device_code", "urn:ietf:params:oauth:grant-type:device_code", Arrays.asList(FlowStep.ACCESS_TOKEN,
                                                                                             FlowStep.POLLING,
                                                                                             FlowStep.REVOKE_TOKEN));

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
