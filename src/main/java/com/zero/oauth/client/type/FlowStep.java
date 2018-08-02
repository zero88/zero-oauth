package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * It describes OAuth flow step that executes request to OAuth server.
 *
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum FlowStep {
    /**
     * The Client issues first established request to Authorization server to identify the delegation request.
     * Then, it obtains an unauthorized Request Token.
     * <p>
     * This step is only applied in OAuth version 1.0/1.0a
     *
     * @see OAuthVersion#V1
     * @see HttpMethod#GET
     */
    REQUEST(OAuthVersion.V1, HttpMethod.GET),

    /**
     * The Client obtains Resource Owner authorization from Authorization server.
     * <ul>
     * <li>{@code OAuth v1.0a}:
     * <p> It is the redirection request to Service Provider that authenticates and obtains Resource Owner's
     * consent. After receiving granted permission, Service Provider backs to Client to complete process.
     * <p> More <a href="https://oauth.net/core/1.0a/#auth_step2">details</a>
     * </li>
     * <li>{@code OAuth v2.0}:
     * <p> Depends on {@link GrantType}, the authorization request can be made directly to the resource
     * owner, or preferably indirectly via the authorization server as an intermediary. The Client will
     * receives a credential representing the resource owner's authorization.
     * </li>
     * </ul>
     *
     * @see OAuthVersion#V1
     * @see OAuthVersion#V2
     * @see HttpMethod#GET
     */
    AUTHORIZE(OAuthVersion.ALL, HttpMethod.GET),

    /**
     * The Client exchange some information with Authorization server to receive the {@code Access Token}.
     * <ul>
     * <li>{@code OAuth v1.0a}: More <a href="https://oauth.net/core/1.0a/#auth_step3">details</a></li>
     * <li>{@code OAuth v2.0}: More <a href="https://tools.ietf.org/html/rfc6749#section-5">details</a></li>
     * </ul>
     *
     * @see OAuthVersion#V1
     * @see OAuthVersion#V2
     * @see HttpMethod#POST
     */
    EXCHANGE_TOKEN(OAuthVersion.ALL, HttpMethod.POST),

    /**
     * The client attempts to acquire an access token every few seconds (at a rate specified by interval) by
     * POSTing to the access token endpoint on the authorization server.
     *
     * @see GrantType#DEVICE_CODE
     * @see HttpMethod#POST
     */
    POLLING(OAuthVersion.V2, HttpMethod.POST),

    /**
     * Revoke {@code access token} or {@code refresh token}.
     *
     * @see HttpMethod#POST
     */
    REVOKE_TOKEN(OAuthVersion.ALL, HttpMethod.POST),

    /**
     * The Client request the protected resource from the Resource server and authenticates by presenting the
     * access token.
     */
    ACCESS_RESOURCE(OAuthVersion.ALL, null);

    private final OAuthVersion version;
    private final HttpMethod method;
}
