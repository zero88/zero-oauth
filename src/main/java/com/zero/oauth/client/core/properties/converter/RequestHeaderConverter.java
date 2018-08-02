package com.zero.oauth.client.core.properties.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.OAuthProperties;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.utils.OAuthEncoder;
import com.zero.oauth.client.utils.Strings;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestHeaderConverter<T extends OAuthProperties> implements PropertiesConverter<T> {

    private static final String EQUAL = "=";
    private static final String SEPARATE = ",";

    private final T properties;

    @Override
    public String serialize(FlowStep step) {
        List<IPropertyModel> by = this.properties.by(step);
        return by.stream().map(this::compute).filter(Objects::nonNull).collect(Collectors.joining(SEPARATE));
    }

    @Override
    public IPropertyStore<IPropertyModel> deserialize(String properties, FlowStep step) {
        Map<String, String> map = new HashMap<>();
        for (String property : properties.split("\\" + SEPARATE)) {
            String[] keyValues = property.split("\\" + EQUAL);
            if (keyValues.length != 2) {
                throw new OAuthParameterException(
                        "Property doesn't conform the syntax: `key`" + EQUAL + "`value`");
            }
            map.put(OAuthEncoder.decode(keyValues[0]),
                    OAuthEncoder.decode(keyValues[1].replaceAll("^\"(.+)\"$", "$1")));
        }
        return this.deserialize(map, step);
    }

    @Override
    public T getPropertyStore() {
        return this.properties;
    }

    private String compute(IPropertyModel property) {
        String key = Strings.requireNotBlank(property.getName());
        Object value = property.validate();
        if (Objects.isNull(value)) {
            return null;
        }
        return new StringBuilder(OAuthEncoder.encode(key)).append(EQUAL).append("\"")
                                                          .append(OAuthEncoder.encode(value.toString()))
                                                          .append("\"").toString();
    }

}
