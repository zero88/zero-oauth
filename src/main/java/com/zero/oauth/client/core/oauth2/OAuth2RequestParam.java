package com.zero.oauth.client.core.oauth2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.client.core.PropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.type.OAuthVersion;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see PropertyModel
 */
public class OAuth2RequestParam extends PropertyModel {

    /**
     * This tells the authorization server that the application is initiating which OAuth grant flow.
     *
     * @see GrantType
     */
    // @formatter:off
    public static final OAuth2RequestParam RESPONSE_TYPE = new OAuth2RequestParam("response_type")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE);
    // @formatter:on

    /**
     * Tells the authorization server where to send the user back to after they approve the request.
     */
    // @formatter:off
    public static final OAuth2RequestParam REDIRECT_URI = new OAuth2RequestParam("redirect_uri")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * The application generates a random string and includes it in the request. It should then check that the same
     * value is returned after the user authorizes the app. This is used to prevent
     * <a href="https://www.owasp.org/index.php/Cross-Site_Request_Forgery_%28CSRF%29">CSRF attacks</a>.
     */
    // @formatter:off
    public static final OAuth2RequestParam STATE = new OAuth2RequestParam("state")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.PASSWORD, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * One or more space-separated strings indicating which permissions the application is requesting. The specific
     * OAuth API you’re using will define the scopes that it supports.
     */
    // @formatter:off
    public static final OAuth2RequestParam SCOPE = new OAuth2RequestParam("scope")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.PASSWORD, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * The public identifier for the application, obtained when the developer first registered the application.
     */
    // @formatter:off
    public static final OAuth2RequestParam CLIENT_ID = new OAuth2RequestParam("client_id")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE)
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.PASSWORD, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * The secret identifier for the application, obtained when the developer first registered the application. This
     * ensures that the request to get the access token is made only from the application, and not from a potential
     * attacker that may have intercepted the authorization code.
     */
    // @formatter:off
    public static final OAuth2RequestParam CLIENT_SECRET = new OAuth2RequestParam("client_secret")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.PASSWORD, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * This tells the token endpoint that the application is using the OAuth grant type.
     */
    // @formatter:off
    public static final OAuth2RequestParam GRANT_TYPE = new OAuth2RequestParam("grant_type")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.IMPLICIT, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.PASSWORD, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * The user's username that they entered in the application.
     */
    // @formatter:off
    public static final OAuth2RequestParam USERNAME = new OAuth2RequestParam("username")
                                                                            .declare(GrantType.PASSWORD, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * The user's password that they entered in the application.
     */
    // @formatter:off
    public static final OAuth2RequestParam PASSWORD = new OAuth2RequestParam("password")
                                                                            .declare(GrantType.PASSWORD, FlowStep.FETCH_TOKEN);
    // @formatter:on

    /**
     * This parameter is the authorization code that the client previously received from the authorization server.
     */
    // @formatter:off
    public static final OAuth2RequestParam CODE = new OAuth2RequestParam("code")
                                                                            .declare(GrantType.AUTH_CODE, FlowStep.FETCH_TOKEN)
                                                                            .declare(GrantType.REFRESH_TOKEN, FlowStep.FETCH_TOKEN, false);
    // @formatter:on

    /**
     * Enables the client to get a new access token without requiring the user to be redirected.
     */
    // @formatter:off
    public static final OAuth2RequestParam REFRESH_TOKEN = new OAuth2RequestParam("refresh_token")
                                                                            .declare(GrantType.REFRESH_TOKEN, FlowStep.FETCH_TOKEN, true);
    // @formatter:on

    private final Map<GrantType, Map<FlowStep, Boolean>> mapping = new HashMap<>();

    public OAuth2RequestParam(String name) {
        super(OAuthVersion.V2, name);
    }

    /**
     * Declare required parameter is used in which <code>GrantType</code> and <code>FlowStep</code>.
     *
     * @param grantType
     *        {@link GrantType}
     * @param step
     *        {@link FlowStep}
     * @return current instance
     * @see GrantType
     * @see FlowStep
     */
    public OAuth2RequestParam declare(GrantType grantType, FlowStep step) {
        return declare(grantType, step, true);
    }

    /**
     * Declare parameter is used in the given <code>GrantType</code> and <code>FlowStep</code>.
     *
     * @param grantType
     *        {@link GrantType}
     * @param step
     *        {@link FlowStep}
     * @param isRequired
     *        Requires value or not
     * @return current instance
     * @see GrantType
     * @see FlowStep
     */
    public OAuth2RequestParam declare(GrantType grantType, FlowStep step, boolean isRequired) {
        validate(grantType, step);
        Map<FlowStep, Boolean> flows = this.mapping.get(grantType);
        if (flows == null) {
            flows = new HashMap<>();
            this.mapping.put(grantType, flows);
        }
        flows.put(step, isRequired);
        return this;
    }

    /**
     * Check it is matched with the given <code>GrantType</code> and <code>FlowStep</code>.
     *
     * @param grantType
     *        {@link GrantType}
     * @param step
     *        {@link FlowStep}
     * @return Custom Property Model
     */
    public PropertyModel check(GrantType grantType, FlowStep step) {
        validate(grantType, step);
        Map<FlowStep, Boolean> flows = this.mapping.get(grantType);
        if (flows == null) {
            return null;
        }
        if (!flows.containsKey(step)) {
            return null;
        }
        PropertyModel prop = new PropertyModel(getVersion(), getName());
        prop.setValue(this.getValue());
        if (flows.get(step)) {
            prop.require();
        }
        return prop;
    }

    @Override
    public <T extends PropertyModel> T require() {
        throw new UnsupportedOperationException("Require value depends on grant type and step");
    }

    private void validate(GrantType grantType, FlowStep step) {
        Objects.requireNonNull(grantType, "OAuth v2.0 grant type cannot be null");
        Objects.requireNonNull(step, "OAuth v2.0 flow step cannot be null");
        if (!this.getVersion().isEqual(step.getVersion())) {
            throw new IllegalArgumentException("Step " + step + " isn't supported in OAuth v" + this.getVersion()
                                                                                                    .getVersion());
        }
    }
}
