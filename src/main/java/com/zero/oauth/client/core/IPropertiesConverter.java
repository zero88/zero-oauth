package com.zero.oauth.client.core;

import com.zero.oauth.client.type.FlowStep;

public interface IPropertiesConverter {

    public <T extends IPropertiesFilter> String serialize(T properties, FlowStep flowStep);

    public <T extends IPropertiesFilter> T deserialize(String properties, FlowStep flowStep);

}
