package com.zero.oauth.client.core.properties.converter;

import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.utils.OAuthEncoder;

public class RequestParamConverter implements IPropertiesConverter {

    private static final String EQUAL = "=";
    private static final String AND = "&";

    @Override
    public <T extends IPropertiesFilter> String serialize(T properties, FlowStep step) {
        return properties.by(step).stream().map(this::combine).filter(Objects::nonNull).collect(Collectors.joining(AND));
    }

    @Override
    public <T extends IPropertiesFilter> T deserialize(String properties, FlowStep flowStep) {
        return null;
    }

    private String combine(IPropertyModel property) {
        Object value = property.validate();
        if (Objects.isNull(value)) {
            return null;
        }
        return new StringBuilder(property.getName()).append(EQUAL).append(OAuthEncoder.encode(value.toString())).toString();
    }
}
