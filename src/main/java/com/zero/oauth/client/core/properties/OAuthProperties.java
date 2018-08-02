package com.zero.oauth.client.core.properties;

import java.util.List;

import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.OAuthVersion;

/**
 * OAuth Properties. It contains a set of method to filter properties in {@code property store}.
 */
public interface OAuthProperties {

    /**
     * Filter properties by OAuth flow step.
     *
     * @param step OAuth step
     * @param <P>  {@code IPropertyModel} type
     * @return A list of properties that conform with given {@code flow step}
     * @see FlowStep
     */
    <P extends IPropertyModel> List<P> by(FlowStep step);

    /**
     * Filter property by OAuth flow step and property name.
     *
     * @param step OAuth step
     * @param name Property Name
     * @param <P>  {@code IPropertyModel} type
     * @return {@code null} if not found property that conform with given inputs.
     */
    <P extends IPropertyModel> P by(FlowStep step, String name);

    /**
     * OAuth version is corresponding to current properties.
     *
     * @return OAuth Version
     */
    OAuthVersion getVersion();

}
