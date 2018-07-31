package com.zero.oauth.client.core.oauth1;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.type.FlowStep;

interface IOAuth1PropertyMatcher extends IPropertyModel {

    /**
     * Declare required property belongs to given {@code FlowStep}.
     *
     * @param step OAuth flow step
     *
     * @return Current instance
     *
     * @see FlowStep
     */
    default <T extends OAuth1PropertyModel> T declare(FlowStep step) {
        return declare(step, Constraint.REQUIRED);
    }

    /**
     * Declare property belongs to given {@code FlowStep} and its constraint.
     *
     * @param step       OAuth Flow step contains property
     * @param constraint Property constraint
     *
     * @return Current instance
     *
     * @see FlowStep
     * @see Constraint
     */
    <T extends OAuth1PropertyModel> T declare(FlowStep step, Constraint constraint);

    /**
     * Check this is matched with the given {@code FlowStep}.
     *
     * @param step {@link FlowStep}
     *
     * @return {@code null} if this property doesn't conform given {@code FlowStep}
     */
    IPropertyModel match(FlowStep step);

}
