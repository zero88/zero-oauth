package com.zero.oauth.client.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * It describes OAuth flow step that executes request to OAuth server.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public enum FlowStep {
    /**
     * The Client issues first established request to Authorization server to identify the delegation request. Then, it
     * obtains an unauthorized Request Token.
     * <br/>
     * This step is only applied in OAuth version 1.0/1.0a
     *
     * @see OAuthVersion#V1
     */
    INIT(OAuthVersion.V1),

    /**
     * The Client obtains Resource Owner authorization from Authorization server.
     * <ul>
     * <li><code>OAuth v1.0a</code>: <br/>
     * It is the redirection request to Service Provider that authenticates and obtains Resource Owner's consent. After
     * receiving granted permission, Service Provider backs to Client to complete process.
     * <br/>
     * More <a href="https://oauth.net/core/1.0a/#auth_step2">details</a>
     * </li>
     * <li><code>OAuth v2.0</code>: <br/>
     * Depends on {@link GrantType}, the authorization request can be made directly to the resource owner, or preferably
     * indirectly via the authorization server as an intermediary.
     * The Client will receives a credential represeting th resource owner's authorization.
     * </li>
     * </ul>
     *
     * @see OAuthVersion#V1
     * @see OAuthVersion#V2
     * @see GrantType
     */
    AUTHORIZE(OAuthVersion.ALL),

    /**
     * The Client exchange some informations with Authorization server to receive the Access Token.
     * <ul>
     * <li><code>OAuth v1.0a</code>: More <a href="https://oauth.net/core/1.0a/#auth_step3">details</a></li>
     * <li><code>OAuth v2.0</code>: More <a href="https://oauth.net/core/1.0a/#auth_step3">details</a></li>
     *
     * @see OAuthVersion#V1
     * @see OAuthVersion#V2
     */
    FETCH_TOKEN(OAuthVersion.ALL),

    /**
     * The Client request the protected resource from the Resource server and authenticates by presenting the access
     * token.
     */
    ACCESS_RESOURCE(OAuthVersion.ALL);

    private final OAuthVersion version;
}
