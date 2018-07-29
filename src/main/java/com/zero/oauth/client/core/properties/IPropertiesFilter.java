package com.zero.oauth.client.core.properties;

import java.util.List;

import com.zero.oauth.client.type.FlowStep;

/**
 * It contains a set of method to filter properties in {@code property store}.
 */
public interface IPropertiesFilter {

    /**
     * Filter properties by OAuth flow step.
     *
     * @param step
     *        OAuth step
     * @return
     * @see FlowStep
     */
    public List<IPropertyModel> by(FlowStep step);

}
