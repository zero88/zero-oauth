package com.zero.oauth.client.core.oauth1;

import java.util.Map;

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

    public static final OAuth1ResponseProp TOKEN =
            new OAuth1ResponseProp("oauth_token").declare(FlowStep.REQUEST).declare(FlowStep.AUTHORIZE)
                                                 .declare(FlowStep.EXCHANGE_TOKEN);

    /**
     * The Token Secret.
     */

    public static final OAuth1ResponseProp TOKEN_SECRET =
            new OAuth1ResponseProp("oauth_token_secret").declare(FlowStep.REQUEST)
                                                        .declare(FlowStep.EXCHANGE_TOKEN);

    /**
     * MUST be present and set to "true". The parameter is used to differentiate from previous versions of the
     * protocol.
     */
    public static final OAuth1ResponseProp CALLBACK_CONFIRMED =
            new OAuth1ResponseProp("oauth_callback_confirmed").declare(FlowStep.REQUEST);
    /**
     * The verification code.
     */
    public static final OAuth1ResponseProp VERIFIER =
            new OAuth1ResponseProp("oauth_verifier").declare(FlowStep.AUTHORIZE);

    private boolean error = false;

    public OAuth1ResponseProp(String name) {
        super(name);
    }

    private OAuth1ResponseProp(String name, Map<FlowStep, Constraint> steps) {
        super(name, steps);
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

    @SuppressWarnings("unchecked")
    @Override
    public OAuth1ResponseProp duplicate() {
        OAuth1ResponseProp prop =
                new OAuth1ResponseProp(this.getName(), this.getMapping()).setValue(this.getValue());
        if (this.isError()) {
            prop.error();
        }
        return prop;
    }

}
