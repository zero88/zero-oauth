package com.zero.oauth.core.oauth2;

import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.GrantType;

/**
 * OAuth v2 matcher.
 *
 * @since 1.0.0
 */
interface OAuth2PropertyMatcher extends IPropertyModel {

    /**
     * Declare required property belongs to given {@code GrantType} and {@code FlowStep}.
     *
     * @param <T>       Type of implementation of property model
     * @param grantType OAuth Grant Type contains property
     * @param step      OAuth Flow step contains property
     * @return Current instance
     * @see GrantType
     * @see FlowStep
     */
    default <T extends OAuth2PropertyModel> T declare(GrantType grantType, FlowStep step) {
        return this.declare(grantType, step, Constraint.REQUIRED);
    }

    /**
     * Declare property belongs to given {@code GrantType} and {@code FlowStep} and its constraint.
     *
     * @param <T>        Type of implementation of property model
     * @param grantType  OAuth Grant Type contains property
     * @param step       OAuth Flow step contains property
     * @param constraint Property constraint
     * @return Current instance
     * @see GrantType
     * @see FlowStep
     * @see Constraint
     */
    <T extends OAuth2PropertyModel> T declare(GrantType grantType, FlowStep step, Constraint constraint);

    /**
     * Check this is matched with the given {@code GrantType} and {@code FlowStep}.
     *
     * @param <T>       Type of implementation of property model
     * @param grantType {@link GrantType}
     * @param step      {@link FlowStep}
     * @return {@code null} if this property doesn't conform given {@code GrantType} and {@code FlowStep}
     */
    <T extends OAuth2PropertyModel> T match(GrantType grantType, FlowStep step);

    /**
     * Check this is matched with the given {@code GrantType}.
     *
     * @param <T>       Type of implementation of property model
     * @param grantType {@link GrantType}
     * @return {@code null} if this property doesn't conform given {@code GrantType}
     */
    <T extends OAuth2PropertyModel> T match(GrantType grantType);

}
