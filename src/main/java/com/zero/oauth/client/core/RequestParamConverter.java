package com.zero.oauth.client.core;

import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;

import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.utils.OAuthEncoder;

public class RequestParamConverter implements IPropertiesConverter {

    private static final String EQUAL = "=";
    private static final String AND = "&";

    @Override
    public <T extends IPropertiesFilter> String serialize(T properties, FlowStep flowStep) {
        return properties.by(flowStep).stream().map(prop -> combine(prop.getName(), prop.getValue())).filter(
                                                                                                             Strings::isNotBlank)
                         .collect(Collectors.joining(AND));
    }

    @Override
    public <T extends IPropertiesFilter> T deserialize(String properties, FlowStep flowStep) {
        return null;
    }

    private String combine(String key, Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return new StringBuilder(key).append(EQUAL).append(OAuthEncoder.encode(value.toString())).toString();
    }
}
