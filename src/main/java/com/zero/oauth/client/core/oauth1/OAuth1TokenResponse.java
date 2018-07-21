package com.zero.oauth.client.core.oauth1;

import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.core.ResponsePropModel;

public class OAuth1TokenResponse extends PropertyList<ResponsePropModel> {

    /**
     * The Request Token.
     */
    public static final ResponsePropModel TOKEN = ResponsePropModel.v1("oauth_token").require();
    /**
     * The Token Secret.
     */
    public static final ResponsePropModel TOKEN_SECRET = ResponsePropModel.v1("oauth_token_secret").require();
    /**
     * MUST be present and set to "true". The parameter is used to differentiate from previous versions of the protocol.
     */
    public static final ResponsePropModel CALLBACK_CONFIRMED = (ResponsePropModel.v1("oauth_callback_confirmed")).require();
}
