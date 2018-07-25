package com.zero.oauth.client.core;

import com.zero.oauth.client.type.FlowStep;

public class RequestHeaderConverter implements IPropertiesConverter {

    @Override
    public <T extends IPropertiesFilter> String serialize(T properties, FlowStep flowStep) {
        return null;
    }

    @Override
    public <T extends IPropertiesFilter> T deserialize(String properties, FlowStep flowStep) {
        return null;
    }

}
