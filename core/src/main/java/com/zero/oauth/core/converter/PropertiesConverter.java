package com.zero.oauth.core.converter;

import java.util.Map;
import java.util.Objects;

import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.properties.IResponseProperties;
import com.zero.oauth.core.properties.IResponseProperty;
import com.zero.oauth.core.properties.PropertyModel;
import com.zero.oauth.core.type.FlowStep;

/**
 * Represents {@code converter} that turns on properties transform to data format.
 *
 * @since 1.0.0
 */
public interface PropertiesConverter {

    /**
     * Convert properties in store to a corresponding data format in given Flow step.
     *
     * @param <P>               Type of {@code IPropertyModel}
     * @param <R>               Type of {@code IRequestProperties}
     * @param requestProperties Request properties
     * @param step              Flow step
     * @return String data with format
     * @see IPropertyModel
     * @see IRequestProperties
     */
    <P extends IPropertyModel, R extends IRequestProperties<P>> String serialize(R requestProperties, FlowStep step);

    /**
     * De-convert string data to {@code property store}.
     *
     * @param properties String properties is response data
     * @return Map of response data
     * @see IResponseProperty
     * @see IResponseProperties
     */
    Map<String, Object> deserialize(String properties);

    /**
     * De-convert string data to {@code property store}.
     *
     * @param <P>        Type of {@code IResponseProperty}
     * @param <T>        Type of {@code IResponseProperties}
     * @param store      Response properties store
     * @param properties String properties is response data
     * @param step       Flow step
     * @return Property store contains all response data
     * @see IResponseProperty
     * @see IResponseProperties
     */
    <P extends IResponseProperty, T extends IResponseProperties<P>> T deserialize(T store, String properties,
                                                                                  FlowStep step);

    /**
     * It is step 2 of {@link #deserialize(IResponseProperties, String, FlowStep)} after decomposed string properties to
     * {@code Key-Value} list.
     *
     * @param <P>               Type of {@code IResponseProperty}
     * @param <S>               Type of {@code IResponseProperties}
     * @param store             Response properties
     * @param deserializeValues Key-Value properties
     * @param step              Flow step
     * @return Property store contains all response data
     */
    default <P extends IResponseProperty, S extends IResponseProperties<P>> S deserialize(S store,
                                                                                          Map<String, ?> deserializeValues,
                                                                                          FlowStep step) {
        deserializeValues.entrySet().stream().map(entry -> compose(store, step, entry.getKey(), entry.getValue()))
                         .filter(Objects::nonNull).forEach(store::add);
        return store;
    }

    /**
     * Compute {@code key} and {@code value} to {@code Property}.
     *
     * @param <P>   Type of {@code IResponseProperty}
     * @param <S>   Type of {@code IResponseProperties}
     * @param store Response properties
     * @param step  Flow step
     * @param key   Property key
     * @param value Property value
     * @return Default property by given key and value
     * @see IPropertyModel
     * @see PropertyModel
     */
    default <P extends IResponseProperty, S extends IResponseProperties<P>> P compose(S store, FlowStep step,
                                                                                      String key, Object value) {
        P property = store.by(step, key);
        if (Objects.isNull(property)) {
            return store.create(key).setValue(value);
        }
        return property.duplicate(value);
    }

}
