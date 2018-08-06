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
import com.zero.oauth.client.utils.Strings;
import com.zero.oauth.client.utils.Urls;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * HTTP Header converter.
 *
 * @param <T> Type of {@code OAuthProperties}
 * @see <a href="https://tools.ietf.org/html/rfc5849#section-3.5.1">Header syntax</a>
 * @see OAuthProperties
 */
@RequiredArgsConstructor
@Getter
public class HttpHeaderConverter<T extends OAuthProperties> implements PropertiesConverter<T> {

    private static final String EQUAL = "=";
    private static final String SEPARATE = ",";

    private final T propertyStore;

    @Override
    public String serialize(FlowStep step) {
        List<IPropertyModel> by = this.propertyStore.by(step);
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
            map.put(Urls.decode(keyValues[0]), Urls.decode(keyValues[1].replaceAll("^\"(.+)\"$", "$1")));
        }
        return this.deserialize(map, step);
    }

    private String compute(IPropertyModel property) {
        String key = Strings.requireNotBlank(property.getName());
        String value = Strings.toString(property.serialize());
        return Strings.isBlank(value) ? null : Urls.encode(key) + EQUAL + "\"" + Urls.encode(value) + "\"";
    }

}
