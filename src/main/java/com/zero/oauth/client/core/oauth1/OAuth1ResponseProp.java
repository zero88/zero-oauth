package com.zero.oauth.client.core.oauth1;

import com.zero.oauth.client.core.IResponsePropModel;
import com.zero.oauth.client.core.PropertyModel;
import com.zero.oauth.client.type.OAuthVersion;

import lombok.Getter;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see PropertyModel
 */
@Getter
public class OAuth1ResponseProp extends PropertyModel implements IResponsePropModel {

    /**
     * The Request Token.
     */
    public static final OAuth1ResponseProp TOKEN = new OAuth1ResponseProp("oauth_token").require();
    /**
     * The Token Secret.
     */
    public static final OAuth1ResponseProp TOKEN_SECRET = new OAuth1ResponseProp("oauth_token_secret").require();
    /**
     * MUST be present and set to "true". The parameter is used to differentiate from previous versions of the protocol.
     */
    public static final OAuth1ResponseProp CALLBACK_CONFIRMED = new OAuth1ResponseProp("oauth_callback_confirmed").require();

    private boolean error = false;

    OAuth1ResponseProp(String name) {
        super(OAuthVersion.V1, name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth1ResponseProp error() {
        this.error = true;
        return this;
    }

    @Override
    public boolean isError() {
        return this.error;
    }

}
