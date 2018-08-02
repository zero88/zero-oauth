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
     * The {@code authorization code} grant type is used to obtain both access tokens and refresh tokens and
     * is optimized for confidential clients.
     */
    AUTH_CODE("code", "authorization_code", Arrays
        .asList(FlowStep.AUTHORIZE, FlowStep.EXCHANGE_TOKEN, FlowStep.REVOKE_TOKEN,
                FlowStep.ACCESS_RESOURCE)),

    /**
     * The {@code implicit} grant type is used to obtain access tokens (it does not support the issuance of
     * refresh tokens) and is optimized for public clients known to operate a particular redirection URI.
     */
    IMPLICIT("token", null,
             Arrays.asList(FlowStep.AUTHORIZE, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE)),

    /**
     * The {@code deprecated implicit} grant type
     */
    IMPLICIT_DEPRECATED("token", "authorization_code", Arrays
        .asList(FlowStep.AUTHORIZE, FlowStep.EXCHANGE_TOKEN, FlowStep.REVOKE_TOKEN,
                FlowStep.ACCESS_RESOURCE)),

    /**
     * The {@code resource owner password credentials} grant type is suitable in cases where the resource
     * owner has a trust relationship with the client, such as the device operating system or a highly
     * privileged application.
     */
    PASSWORD(null, "password",
             Arrays.asList(FlowStep.EXCHANGE_TOKEN, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE)),

    /**
     * The client can request an access token using only its {@code client credentials} (or other supported
     * means of authentication) when the client is requesting access to the protected resources under its
     * control, or those of another resource owner that have been previously arranged with the authorization
     * server (the method of which is beyond the scope of this specification).
     */
    CLIENT_CREDENTIALS(null, "client_credentials", Arrays
        .asList(FlowStep.EXCHANGE_TOKEN, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE)),

    /**
     * If the authorization server issued a {@code refresh token} to the client, client is able to issue a
     * call to exchange new access token to replace the old one is expired.
     */
    REFRESH_TOKEN(null, "refresh_token", Arrays.asList(FlowStep.EXCHANGE_TOKEN, FlowStep.REVOKE_TOKEN)),

    /**
     * {@code Device code}
     * <a href="https://oauth.net/2/grant-types/device-code/">Device code specs</a>.
     */
    DEVICE_CODE("device_code", "urn:ietf:params:oauth:grant-type:device_code", Arrays
        .asList(FlowStep.EXCHANGE_TOKEN, FlowStep.POLLING, FlowStep.REVOKE_TOKEN, FlowStep.ACCESS_RESOURCE));

    private final String responseType;
    private final String type;
    /**
     * Available steps.
     */
    private final List<FlowStep> steps;

    @Override
    public String toString() {
        return Strings.isNotBlank(type) ? type : responseType;
    }
}
