package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.core.properties.PropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see PropertyModel
 * @see OAuth2PropertyModel
 */
public class OAuth2RequestProp extends OAuth2PropertyModel {

    public OAuth2RequestProp(String name) {
        super(name);
    }

    /**
     * This tells the authorization server that the application is initiating which OAuth grant flow.
     *
     * @see GrantType
     */
    // @formatter:off
    public static final OAuth2RequestProp RESPONSE_TYPE = new OAuth2RequestProp("response_type")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL);
    // @formatter:on

    /**
     * Tells the authorization server where to send the user back to after they approve the request.
     */
    // @formatter:off
    public static final OAuth2RequestProp REDIRECT_URI = new OAuth2RequestProp("redirect_uri")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN);
    // @formatter:on

    /**
     * One or more space-separated strings indicating which permissions the application is requesting. The specific
     * OAuth API you’re using will define the scopes that it supports.
     */
    // @formatter:off
    public static final OAuth2RequestProp SCOPE = new OAuth2RequestProp("scope")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL);
    // @formatter:on

    /**
     * The application generates a random string and includes it in the request. It should then check that the same
     * value is returned after the user authorizes the app. This is used to prevent
     * <a href="https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29">CSRF attacks</a>.
     */
    // @formatter:off
    public static final OAuth2RequestProp STATE = new OAuth2RequestProp("state")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE, Constraint.RECOMMENDATION)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE, Constraint.RECOMMENDATION)
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN, Constraint.RECOMMENDATION)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN, Constraint.RECOMMENDATION);
    // @formatter:on

    /**
     * The public identifier for the application, obtained when the developer first registered the application.
     */
    // @formatter:off
    public static final OAuth2RequestProp CLIENT_ID = new OAuth2RequestProp("client_id")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.DEVICE_CODE, FlowStep.POLLING);
    // @formatter:on

    /**
     * The secret identifier for the application, obtained when the developer first registered the application. This
     * ensures that the request to get the access token is made only from the application, and not from a potential
     * attacker that may have intercepted the authorization code.
     */
    // @formatter:off
    public static final OAuth2RequestProp CLIENT_SECRET = new OAuth2RequestProp("client_secret")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.DEVICE_CODE, FlowStep.POLLING);
    // @formatter:on

    /**
     * This tells the token endpoint that the application is using the OAuth grant type.
     */
    // @formatter:off
    public static final OAuth2RequestProp GRANT_TYPE = new OAuth2RequestProp("grant_type")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN)
                                                                            .declare(GrantType.DEVICE_CODE, FlowStep.POLLING)
                                                                            .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN);
    // @formatter:on

    /**
     * The user's username that they entered in the application.
     */
    // @formatter:off
    public static final OAuth2RequestProp USERNAME = new OAuth2RequestProp("username")
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN);
    // @formatter:on

    /**
     * The user's password that they entered in the application.
     */
    // @formatter:off
    public static final OAuth2RequestProp PASSWORD = new OAuth2RequestProp("password")
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN);
    // @formatter:on

    /**
     * This parameter is the authorization code that the client previously received from the authorization server. It
     * will be used to exchange for {@code access token}
     */
    // @formatter:off
    public static final OAuth2RequestProp CODE = new OAuth2RequestProp("code")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.POLLING)
                                                                        .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL);
    // @formatter:on

    /**
     * Enables the client to get a new access token without requiring the user to be redirected.
     */
    // @formatter:off
    public static final OAuth2RequestProp REFRESH_TOKEN = new OAuth2RequestProp("refresh_token")
                                                                            .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN);
    // @formatter:on

    /**
     * Access token is added in request that call to resource server
     */
    // @formatter:off
    public static final OAuth2RequestProp TOKEN = new OAuth2RequestProp("access_token")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_RESOURCE)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.ACCESS_RESOURCE)
                                                                            .declare(GrantType.PASSWORD, FlowStep.ACCESS_RESOURCE)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_RESOURCE)
                                                                            .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_RESOURCE);
    // @formatter:on
}
