package com.zero.oauth.client.core.properties;

import java.util.List;

import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.OAuthVersion;

/**
 * Request OAuth Properties. It contains a set of method to filter properties in {@code property store}.
 *
 * @param <P> {@code IPropertyModel} type
 * @since 1.0.0
 */
public interface RequestProperties<P extends IPropertyModel> extends IPropertyStore<P> {

    /**
     * Filter properties by OAuth flow step.
     *
     * @param step OAuth step
     * @return A list of properties that conform with given {@code flow step}
     * @see FlowStep
     */
    List<P> by(FlowStep step);

    /**
     * Filter property by OAuth flow step and property name.
     *
     * @param step OAuth step
     * @param name Property Name
     * @return {@code null} if not found property that conform with given inputs.
     */
    P by(FlowStep step, String name);

    /**
     * OAuth version is corresponding to current properties.
     *
     * @return OAuth Version
     */
    OAuthVersion getVersion();

}
