package com.zero.oauth.core.oauth1;

import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.type.FlowStep;

/**
 * OAuth v1 matcher.
 *
 * @since 1.0.0
 */
interface OAuth1PropertyMatcher extends IPropertyModel {

    /**
     * Declare required property belongs to given {@code FlowStep}.
     *
     * @param <T>  Type of implementation of property model
     * @param step OAuth flow step
     * @return Current instance
     * @see FlowStep
     */
    default <T extends OAuth1PropertyModel> T declare(FlowStep step) {
        return declare(step, Constraint.REQUIRED);
    }

    /**
     * Declare property belongs to given {@code FlowStep} and its constraint.
     *
     * @param <T>        Type of implementation of property model
     * @param step       OAuth Flow step contains property
     * @param constraint Property constraint
     * @return Current instance
     * @see FlowStep
     * @see Constraint
     */
    <T extends OAuth1PropertyModel> T declare(FlowStep step, Constraint constraint);

    /**
     * Check this is matched with the given {@code FlowStep}.
     *
     * @param <T>  Type of implementation of property model
     * @param step {@link FlowStep}
     * @return {@code null} if this property doesn't conform given {@code FlowStep}
     */
    <T extends OAuth1PropertyModel> T match(FlowStep step);

}
