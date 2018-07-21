package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.core.ResponsePropModel;
import com.zero.oauth.client.type.GrantType;

/**
 * Mostly for {@link GrantType#DEVICE_CODE}
 */
public class OAuth2AuthorizationResponse extends PropertyList<ResponsePropModel> {

    /**
     * A value that OAuth server uniquely assigns to identify the device that runs the app requesting authorization. The
     * user will be authorizing that device from another device with richer input capabilities.
     * This code lets the device running the app securely determine whether the user has granted or denied access.
     */
    public static final ResponsePropModel DEVICE_CODE = ResponsePropModel.v2("device_code").require();
    /**
     * A case-sensitive value that identifies to OAuth scopes that the application is requesting access to. Your
     * user interface will instruct the user to enter this value on a separate device with richer input capabilities.
     */
    public static final ResponsePropModel USER_CODE = ResponsePropModel.v2("user_code").require();
    /**
     * A URL that the user must navigate to, on a separate device, to enter the user_code and grant or deny access to
     * your application. Your user interface will also display this value.
     */
    public static final ResponsePropModel VERIFICATION_URL = ResponsePropModel.v2("verification_url").require();
    /**
     * The length of time, in seconds, that the device_code and user_code are valid.
     */
    public static final ResponsePropModel EXPIRES_IN = ResponsePropModel.v2("expires_in").require();
    /**
     * The length of time, in seconds, that your device should wait between polling requests.
     */
    public static final ResponsePropModel INTERVAL = ResponsePropModel.v2("interval").require();

}
