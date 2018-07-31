package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

interface IOAuth2PropertyMatcher extends IPropertyModel {

    /**
     * Declare required property belongs to given {@code GrantType} and {@code FlowStep}.
     *
     * @param grantType OAuth Grant Type contains property
     * @param step      OAuth Flow step contains property
     *
     * @return Current instance
     *
     * @see GrantType
     * @see FlowStep
     */
    default <T extends OAuth2PropertyModel> T declare(GrantType grantType, FlowStep step) {
        return this.declare(grantType, step, Constraint.REQUIRED);
    }

    /**
     * Declare property belongs to given {@code GrantType} and {@code FlowStep} and its constraint.
     *
     * @param grantType  OAuth Grant Type contains property
     * @param step       OAuth Flow step contains property
     * @param constraint Property constraint
     *
     * @return Current instance
     *
     * @see GrantType
     * @see FlowStep
     * @see Constraint
     */
    <T extends OAuth2PropertyModel> T declare(GrantType grantType, FlowStep step,
                                              Constraint constraint);

    /**
     * Check this is matched with the given {@code GrantType} and {@code FlowStep}.
     *
     * @param grantType {@link GrantType}
     * @param step      {@link FlowStep}
     *
     * @return {@code null} if this property doesn't conform given {@code GrantType} and {@code
     *         FlowStep}
     */
    IPropertyModel match(GrantType grantType, FlowStep step);

    /**
     * Check this is matched with the given {@code GrantType}.
     *
     * @param grantType {@link GrantType}
     *
     * @return {@code null} if this property doesn't conform given {@code GrantType}
     */
    <T extends IPropertyModel> T match(GrantType grantType);

}
