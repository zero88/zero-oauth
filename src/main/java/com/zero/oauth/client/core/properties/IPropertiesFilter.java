package com.zero.oauth.client.core.properties;

import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.OAuthVersion;

import java.util.List;

/**
 * It contains a set of method to filter properties in {@code property store}.
 */
public interface IPropertiesFilter {

    /**
     * Filter properties by OAuth flow step.
     *
     * @param step OAuth step
     * @param <P>  {@code IPropertyModel} type
     * @return A list of properties that conform with given {@code flow step}
     * @see FlowStep
     */
    public <P extends IPropertyModel> List<P> by(FlowStep step);

    /**
     * Filter property by OAuth flow step and property name.
     *
     * @param step OAuth step
     * @param name Property Name
     * @param <P>  {@code IPropertyModel} type
     * @return {@code null} if not found property that conform with given inputs.
     */
    public <P extends IPropertyModel> P by(FlowStep step, String name);

    /**
     *
     * @return OAuth Version
     */
    OAuthVersion getVersion();
}
