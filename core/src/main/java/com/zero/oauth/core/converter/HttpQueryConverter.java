package com.zero.oauth.core.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.core.exceptions.OAuthParameterException;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.properties.IResponseProperties;
import com.zero.oauth.core.properties.IResponseProperty;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.utils.Strings;
import com.zero.oauth.core.utils.Urls;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * HTTP Query converter.
 *
 * @see <a href="https://tools.ietf.org/html/rfc3986#section-3.4">Query syntax</a>
 */
@RequiredArgsConstructor
@Getter
public final class HttpQueryConverter implements PropertiesConverter {

    private static final String EQUAL = "=";
    private static final String SEPARATE = "&";

    @Override
    public <P extends IPropertyModel, T extends IRequestProperties<P>> String serialize(T requestProperties,
                                                                                        FlowStep step) {
        return requestProperties.by(step).stream().map(this::compute).filter(Objects::nonNull).collect(
            Collectors.joining(SEPARATE));
    }

    @Override
    public Map<String, Object> deserialize(String properties) {
        Map<String, Object> map = new HashMap<>();
        for (String property : properties.split("\\" + SEPARATE)) {
            String[] keyValues = property.split("\\" + EQUAL);
            if (keyValues.length != 2) {
                throw new OAuthParameterException("Property doesn't conform the syntax: `key`" + EQUAL + "`value`");
            }
            map.put(Urls.decode(keyValues[0]), Urls.decode(keyValues[1]));
        }
        return map;
    }

    @Override
    public <P extends IResponseProperty, T extends IResponseProperties<P>> T deserialize(T store, String properties,
                                                                                         FlowStep step) {
        return this.deserialize(store, this.deserialize(properties), step);
    }

    private String compute(IPropertyModel property) {
        String key = Urls.encode(Strings.requireNotBlank(property.getName()));
        String value = Strings.toString(property.serialize());
        return Strings.isBlank(value) ? null : key + EQUAL + Urls.encode(value);
    }

}
