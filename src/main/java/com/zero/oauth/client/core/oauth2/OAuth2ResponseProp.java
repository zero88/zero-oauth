package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IResponsePropModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

import lombok.Getter;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see IPropertyModel
 */
@Getter
public class OAuth2ResponseProp extends OAuth2PropertyModel implements IResponsePropModel {

    /**
     * The authorization token string as issued by the authorization server.
     */
 // @formatter:off
    public static final OAuth2ResponseProp AUTH_TOKEN = new OAuth2ResponseProp("token")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE);
 // @formatter:on

    /**
     * The access token string as issued by the authorization server.
     */
 // @formatter:off
    public static final OAuth2ResponseProp AUTH_STATE = new OAuth2ResponseProp("state")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.AUTHORIZE, Constraint.RECOMMENDATION);
 // @formatter:on

    /**
     * The access token string as issued by the authorization server.
     */
 // @formatter:off
    public static final OAuth2ResponseProp ACCESS_TOKEN = new OAuth2ResponseProp("access_token")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                                        .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.POLLING);
 // @formatter:on

    /**
     * The type of token this is, typically just the string "bearer".
     */
 // @formatter:off
    public static final OAuth2ResponseProp TOKEN_TYPE = new OAuth2ResponseProp("token_type")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE)
                                                                        .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.POLLING).defaultValue("bearer");
 // @formatter:on

    /**
     * The length of time, in seconds, that response are valid.
     */
 // @formatter:off
    public static final OAuth2ResponseProp EXPIRES_IN = new OAuth2ResponseProp("expires_in")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN, Constraint.RECOMMENDATION)
                                                                        .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE, Constraint.RECOMMENDATION)
                                                                        .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN, Constraint.RECOMMENDATION)
                                                                        .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN, Constraint.RECOMMENDATION)
                                                                        .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN, Constraint.RECOMMENDATION)
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN)
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.POLLING, Constraint.RECOMMENDATION);
 // @formatter:on
    /**
     * If the access token will expire, then it is useful to return a refresh token which
     * applications can use to obtain another access token. However, tokens issued with the implicit
     * grant cannot be issued a refresh token.
     */
 // @formatter:off
    public static final OAuth2ResponseProp REFRESH_TOKEN = new OAuth2ResponseProp("refresh_token")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.POLLING, Constraint.OPTIONAL);
 // @formatter:on

    /**
     * If the scope the user granted is identical to the scope the app requested, this parameter is
     * optional. If the granted scope is different from the requested scope, such as if the user
     * modified the scope, then this parameter is required.
     */
 // @formatter:off
    public static final OAuth2ResponseProp SCOPE = new OAuth2ResponseProp("scope")
                                                                        .declare(GrantType.AUTH_CODE, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.IMPLICIT, FlowStep.AUTHORIZE, Constraint.OPTIONAL)
                                                                        .declare(GrantType.PASSWORD, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.CLIENT_CREDENTIALS, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.REFRESH_TOKEN, FlowStep.ACCESS_TOKEN, Constraint.OPTIONAL)
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.POLLING, Constraint.OPTIONAL);
 // @formatter:on

    /**
     * A value that OAuth server uniquely assigns to identify the device that runs the app requesting authorization. The
     * user will be authorizing that device from another device with richer input capabilities.
     * This code lets the device running the app securely determine whether the user has granted or denied access.
     */
 // @formatter:off
    public static final OAuth2ResponseProp DEVICE_CODE = new OAuth2ResponseProp("device_code")
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN);
 // @formatter:on

    /**
     * A case-sensitive value that identifies to OAuth scopes that the application is requesting access to. Your
     * user interface will instruct the user to enter this value on a separate device with richer input capabilities.
     */
 // @formatter:off
    public static final OAuth2ResponseProp USER_CODE = new OAuth2ResponseProp("user_code")
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN);
 // @formatter:on

    /**
     * A URL that the user must navigate to, on a separate device, to enter the user_code and grant or deny access to
     * your application. Your user interface will also display this value.
     */
 // @formatter:off
    public static final OAuth2ResponseProp VERIFICATION_URL = new OAuth2ResponseProp("verification_url")
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN);
 // @formatter:on

    /**
     * The length of time, in seconds, that your device should wait between polling requests.
     */
 // @formatter:off
    public static final OAuth2ResponseProp INTERVAL = new OAuth2ResponseProp("interval")
                                                                        .declare(GrantType.DEVICE_CODE, FlowStep.ACCESS_TOKEN);
 // @formatter:on

    /**
     * Error reason.
     */
    public static final OAuth2ResponseProp ERROR = (new OAuth2ResponseProp("error").error()).constraint(Constraint.REQUIRED);
    /**
     * A great place to link to your API documentation for information about how to correct the
     * specific error that was encountered.
     */
    public static final OAuth2ResponseProp ERROR_URI = (new OAuth2ResponseProp("error_uri").error()).constraint(Constraint.OPTIONAL);
    /**
     * A sentence or two at most describing the circumstance of the error
     */
    public static final OAuth2ResponseProp ERROR_DESCRIPTION = new OAuth2ResponseProp("error_description").error().constraint(Constraint.OPTIONAL);

    private boolean error = false;

    public OAuth2ResponseProp(String name) {
        super(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2ResponseProp error() {
        this.error = true;
        return this;
    }

    @Override
    public boolean isError() {
        return this.error;
    }

}
