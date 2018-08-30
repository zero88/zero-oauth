package com.zero.oauth.core.oauth1;

import com.zero.oauth.core.properties.IResponseProperty;
import com.zero.oauth.core.properties.PropertyModel;
import com.zero.oauth.core.type.FlowStep;

import lombok.Getter;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see PropertyModel
 */
@Getter
public class OAuth1ResponseProperty extends OAuth1PropertyModel implements IResponseProperty {

    /**
     * The Request Token or Access Token.
     */
    public static final OAuth1ResponseProperty TOKEN = new OAuth1ResponseProperty("oauth_token").declare(
        FlowStep.REQUEST).declare(FlowStep.AUTHORIZE).declare(FlowStep.EXCHANGE_TOKEN);

    /**
     * The Token Secret.
     */
    public static final OAuth1ResponseProperty TOKEN_SECRET = new OAuth1ResponseProperty("oauth_token_secret").declare(
        FlowStep.REQUEST).declare(FlowStep.EXCHANGE_TOKEN);

    /**
     * MUST be present and set to "true". The parameter is used to differentiate from previous versions of the
     * protocol.
     */
    public static final OAuth1ResponseProperty CALLBACK_CONFIRMED = new OAuth1ResponseProperty(
        "oauth_callback_confirmed").declare(FlowStep.REQUEST);
    /**
     * The verification code.
     */
    public static final OAuth1ResponseProperty VERIFIER = new OAuth1ResponseProperty("oauth_verifier").declare(
        FlowStep.AUTHORIZE);

    private boolean error = false;

    public OAuth1ResponseProperty(String name) {
        super(name);
    }

    private OAuth1ResponseProperty(OAuth1ResponseProperty property) {
        super(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth1ResponseProperty error() {
        this.error = true;
        return this;
    }

    @Override
    public boolean isError() {
        return this.error;
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth1ResponseProperty duplicate() {
        OAuth1ResponseProperty prop = new OAuth1ResponseProperty(this);
        return this.isError() ? prop.error() : prop;
    }

}
