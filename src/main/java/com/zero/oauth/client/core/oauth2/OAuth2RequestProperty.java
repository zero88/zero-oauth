package com.zero.oauth.client.core.oauth2;

import java.util.Map;

import com.zero.oauth.client.core.properties.PropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see PropertyModel
 * @see OAuth2PropertyModel
 */
public class OAuth2RequestProperty extends OAuth2PropertyModel {

    /**
     * This tells the authorization server that the application is initiating which OAuth grant flow.
     *
     * @see GrantType
     */
    public static final OAuth2RequestProperty RESPONSE_TYPE =
        new OAuth2RequestProperty("response_type").declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                  .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                  .declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN,
                                                           Constraint.OPTIONAL);

    /**
     * Tells the authorization server where to send the user back to after they approve the request.
     */
    public static final OAuth2RequestProperty REDIRECT_URI =
        new OAuth2RequestProperty("redirect_uri").declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                 .declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN)
                                                 .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                 .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN)
                                                 .declare(GrantType.CLIENT_CREDENTIALS,
                                                          FlowStep.EXCHANGE_TOKEN);

    /**
     * One or more space-separated strings indicating which permissions the application is requesting. The
     * specific OAuth API you’re using will define the scopes that it supports.
     */
    public static final OAuth2RequestProperty SCOPE =
        new OAuth2RequestProperty("scope").declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                          .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                          .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN,
                                                   Constraint.OPTIONAL)
                                          .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN)
                                          .declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN)
                                          .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN,
                                                   Constraint.OPTIONAL);

    /**
     * The application generates a random string and includes it in the request. It should then check that the
     * same value is returned after the user authorizes the app. This is used to prevent <a
     * href="https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29">CSRF attacks</a>.
     */
    public static final OAuth2RequestProperty STATE = new OAuth2RequestProperty("state")
        .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE, Constraint.RECOMMENDATION)
        .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE, Constraint.RECOMMENDATION)
        .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN, Constraint.RECOMMENDATION)
        .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN, Constraint.RECOMMENDATION);

    /**
     * The public identifier for the application, obtained when the developer first registered the
     * application.
     */
    public static final OAuth2RequestProperty CLIENT_ID =
        new OAuth2RequestProperty("client_id").declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                              .declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN)
                                              .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                              .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN)
                                              .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN)
                                              .declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN)
                                              .declare(GrantType.DEVICE_CODE, FlowStep.POLLING);

    /**
     * The secret identifier for the application, obtained when the developer first registered the
     * application. This ensures that the request to get the access token is made only from the application,
     * and not from a potential attacker that may have intercepted the authorization code.
     */
    public static final OAuth2RequestProperty CLIENT_SECRET =
        new OAuth2RequestProperty("client_secret").declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN)
                                                  .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN)
                                                  .declare(GrantType.CLIENT_CREDENTIALS,
                                                           FlowStep.EXCHANGE_TOKEN)
                                                  .declare(GrantType.DEVICE_CODE, FlowStep.POLLING);

    /**
     * This tells the token endpoint that the application is using the OAuth grant type.
     */
    public static final OAuth2RequestProperty GRANT_TYPE =
        new OAuth2RequestProperty("grant_type").declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN)
                                               .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN)
                                               .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN)
                                               .declare(GrantType.DEVICE_CODE, FlowStep.POLLING)
                                               .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN);

    /**
     * The user's username that they entered in the application.
     */
    public static final OAuth2RequestProperty USERNAME =
        new OAuth2RequestProperty("username").declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN);

    /**
     * The user's password that they entered in the application.
     */
    public static final OAuth2RequestProperty PASSWORD =
        new OAuth2RequestProperty("password").declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN);

    /**
     * This parameter is the authorization code that the client previously received from the authorization
     * server. It will be used to exchange for {@code access token}.
     */
    public static final OAuth2RequestProperty CODE =
        new OAuth2RequestProperty("code").declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN)
                                         .declare(GrantType.DEVICE_CODE, FlowStep.POLLING)
                                         .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN,
                                                  Constraint.OPTIONAL);

    /**
     * Enables the client to get a new access token without requiring the user to be redirected.
     */
    public static final OAuth2RequestProperty REFRESH_TOKEN =
        new OAuth2RequestProperty("refresh_token").declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN);

    /**
     * Access token is added in request that call to resource server.
     */
    public static final OAuth2RequestProperty TOKEN =
        new OAuth2RequestProperty("access_token").declare(GrantType.AUTH_CODE, FlowStep.ACCESS_RESOURCE)
                                                 .declare(GrantType.IMPLICIT, FlowStep.ACCESS_RESOURCE)
                                                 .declare(GrantType.PASSWORD, FlowStep.ACCESS_RESOURCE)
                                                 .declare(GrantType.CLIENT_CREDENTIALS,
                                                          FlowStep.ACCESS_RESOURCE)
                                                 .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_RESOURCE);

    public OAuth2RequestProperty(String name) {
        super(name);
    }

    private OAuth2RequestProperty(String name, Map<GrantType, Map<FlowStep, Constraint>> mapping) {
        super(name, mapping);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2RequestProperty duplicate() {
        return new OAuth2RequestProperty(this.getName(), this.getMapping()).setValue(this.getValue());
    }

}
