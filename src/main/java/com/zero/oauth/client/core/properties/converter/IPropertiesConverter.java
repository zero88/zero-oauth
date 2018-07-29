package com.zero.oauth.client.core.properties.converter;

import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.type.FlowStep;

/**
 * Represents {@code converter} that turns tranfomration properties to data format.
 */
public interface IPropertiesConverter {

    public <T extends IPropertiesFilter> String serialize(T properties, FlowStep flowStep);

    public <T extends IPropertiesFilter> T deserialize(String properties, FlowStep flowStep);

}
