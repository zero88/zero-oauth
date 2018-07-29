package com.zero.oauth.client.core.oauth1;

import com.zero.oauth.client.core.properties.IResponsePropModel;
import com.zero.oauth.client.core.properties.PropertyModel;
import com.zero.oauth.client.type.FlowStep;
import lombok.Getter;

/**
 * It is model to define an OAuth parameter when sending request OAuth server.
 *
 * @see PropertyModel
 */
@Getter
public class OAuth1ResponseProp extends OAuth1PropertyModel implements IResponsePropModel {

    /**
     * The Request Token or Access Token.
     */
 // @formatter:off
    public static final OAuth1ResponseProp TOKEN = new OAuth1ResponseProp("oauth_token").declare(FlowStep.INIT)
                                                                                        .declare(FlowStep.AUTHORIZE)
                                                                                        .declare(FlowStep.ACCESS_TOKEN);
 // @formatter:on

    /**
     * The Token Secret.
     */
 // @formatter:off
    public static final OAuth1ResponseProp TOKEN_SECRET = new OAuth1ResponseProp("oauth_token_secret").declare(FlowStep.INIT)
                                                                                                      .declare(FlowStep.ACCESS_TOKEN);
 // @formatter:on

    /**
     * MUST be present and set to "true". The parameter is used to differentiate from previous versions of the protocol.
     */
    public static final OAuth1ResponseProp CALLBACK_CONFIRMED = new OAuth1ResponseProp("oauth_callback_confirmed").declare(FlowStep.INIT);
    /**
     * The verification code.
     */
    public static final OAuth1ResponseProp VERIFIER = new OAuth1ResponseProp("oauth_verifier").declare(FlowStep.AUTHORIZE);

    private boolean error = false;

    public OAuth1ResponseProp(String name) {
        super(name);
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
