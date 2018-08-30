package com.zero.oauth.core.oauth2;

import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.properties.IResponseProperty;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.GrantType;

import lombok.Getter;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see IPropertyModel
 */
@Getter
public class OAuth2ResponseProperty extends OAuth2PropertyModel implements IResponseProperty {

    /**
     * The authorization token string as issued by the authorization server.
     */
    public static final OAuth2ResponseProperty TOKEN_CODE =
        new OAuth2ResponseProperty("code").declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE);

    /**
     * The access token string as issued by the authorization server.
     */
    public static final OAuth2ResponseProperty AUTH_STATE =
        new OAuth2ResponseProperty("state").declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE, Constraint.RECOMMENDATION);

    /**
     * The access token string as issued by the authorization server.
     */
    public static final OAuth2ResponseProperty ACCESS_TOKEN =
        new OAuth2ResponseProperty("access_token").declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN)
                                                  .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                  .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN)
                                                  .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN)
                                                  .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN)
                                                  .declare(GrantType.DEVICE_CODE, FlowStep.POLLING);

    /**
     * The type of token this is, typically just the string "bearer".
     */
    public static final OAuth2ResponseProperty TOKEN_TYPE =
        new OAuth2ResponseProperty("token_type").declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN)
                                                .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN)
                                                .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN)
                                                .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN)
                                                .declare(GrantType.DEVICE_CODE, FlowStep.POLLING).setValue("bearer");

    /**
     * The length of time, in seconds, that response are valid.
     */
    public static final OAuth2ResponseProperty EXPIRES_IN =
        new OAuth2ResponseProperty("expires_in")
            .declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN,
                     Constraint.RECOMMENDATION)
            .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE,
                     Constraint.RECOMMENDATION)
            .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN,
                     Constraint.RECOMMENDATION)
            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN,
                     Constraint.RECOMMENDATION)
            .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN,
                     Constraint.RECOMMENDATION)
            .declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN)
            .declare(GrantType.DEVICE_CODE, FlowStep.POLLING,
                     Constraint.RECOMMENDATION);

    /**
     * If the access token will expire, then it is useful to return a refresh token which applications can use to obtain
     * another access token. However, tokens issued with the implicit grant cannot be issued a refresh token.
     */
    public static final OAuth2ResponseProperty REFRESH_TOKEN =
        new OAuth2ResponseProperty("refresh_token")
            .declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN,
                     Constraint.OPTIONAL)
            .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN,
                     Constraint.OPTIONAL)
            .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN,
                     Constraint.OPTIONAL)
            .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN,
                     Constraint.OPTIONAL)
            .declare(GrantType.DEVICE_CODE, FlowStep.POLLING,
                     Constraint.OPTIONAL);

    /**
     * If the scope the user granted is identical to the scope the app requested, this parameter is optional. If the
     * granted scope is different from the requested scope, such as if the user modified the scope, then this parameter
     * is required.
     */
    public static final OAuth2ResponseProperty SCOPE =
        new OAuth2ResponseProperty("scope").declare(GrantType.AUTH_CODE, FlowStep.EXCHANGE_TOKEN, Constraint.OPTIONAL)
                                           .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE, Constraint.OPTIONAL)
                                           .declare(GrantType.PASSWORD, FlowStep.EXCHANGE_TOKEN, Constraint.OPTIONAL)
                                           .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.EXCHANGE_TOKEN,
                                                    Constraint.OPTIONAL)
                                           .declare(GrantType.REFRESH_TOKEN, FlowStep.EXCHANGE_TOKEN,
                                                    Constraint.OPTIONAL)
                                           .declare(GrantType.DEVICE_CODE, FlowStep.POLLING, Constraint.OPTIONAL);

    /**
     * A value that OAuth server uniquely assigns to identify the device that runs the app requesting authorization. The
     * user will be authorizing that device from another device with richer input capabilities. This code lets the
     * device running the app securely determine whether the user has granted or denied access.
     */
    public static final OAuth2ResponseProperty DEVICE_CODE =
        new OAuth2ResponseProperty("device_code").declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN);

    /**
     * A case-sensitive value that identifies to OAuth scopes that the application is requesting access to. Your user
     * interface will instruct the user to enter this value on a separate device with richer input capabilities.
     */
    public static final OAuth2ResponseProperty USER_CODE =
        new OAuth2ResponseProperty("user_code").declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN);

    /**
     * A URL that the user must navigate to, on a separate device, to enter the user_code and grant or deny access to
     * your application. Your user interface will also display this value.
     */
    public static final OAuth2ResponseProperty VERIFICATION_URL =
        new OAuth2ResponseProperty("verification_url").declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN);

    /**
     * The length of time, in seconds, that your device should wait between polling requests.
     */
    public static final OAuth2ResponseProperty INTERVAL =
        new OAuth2ResponseProperty("interval").declare(GrantType.DEVICE_CODE, FlowStep.EXCHANGE_TOKEN);

    /**
     * Error reason.
     */
    public static final OAuth2ResponseProperty ERROR_CODE =
        (new OAuth2ResponseProperty("error").error()).constraint(Constraint.REQUIRED);
    /**
     * A great place to link to your API documentation for information about how to correct the specific error that was
     * encountered.
     */
    public static final OAuth2ResponseProperty ERROR_URI =
        (new OAuth2ResponseProperty("error_uri").error()).constraint(Constraint.OPTIONAL);
    /**
     * A sentence or two at most describing the circumstance of the error.
     */
    public static final OAuth2ResponseProperty ERROR_DESCRIPTION =
        new OAuth2ResponseProperty("error_description").error().constraint(Constraint.OPTIONAL);

    private boolean error = false;

    public OAuth2ResponseProperty(String name) {
        super(name);
    }

    private OAuth2ResponseProperty(OAuth2ResponseProperty property) {
        super(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2ResponseProperty error() {
        this.error = true;
        return this;
    }

    @Override
    public boolean isError() {
        return this.error;
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2ResponseProperty duplicate() {
        OAuth2ResponseProperty prop = new OAuth2ResponseProperty(this);
        return this.isError() ? prop.error() : prop;
    }

}
