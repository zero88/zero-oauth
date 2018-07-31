package com.zero.oauth.client.core.properties.converter;

import java.util.Map;
import java.util.Objects;

import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.PropertyModel;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.type.FlowStep;

/**
 * Represents {@code converter} that turns transformation properties to data format.
 *
 * @param T Property store type that implemented {@link IPropertiesConverter}
 *
 * @see PropertyStore
 */
public interface IPropertiesConverter<T extends IPropertiesFilter> {

    /**
     * Convert properties in store to a corresponding data format in given Flow step.
     *
     * @param step Flow step
     *
     * @return String data with format
     */
    String serialize(FlowStep step);

    /**
     * De-convert string data to {@code property store}.
     *
     * @param properties String properties is response data
     * @param step       Flow step
     *
     * @return Property store contains all response data
     */
    PropertyStore<IPropertyModel> deserialize(String properties, FlowStep step);

    /**
     * It is step 2 of {@link #deserialize(String, FlowStep)} after decomposed string properties to
     * {@code Key-Value} list.
     *
     * @param deserializeValues Key-Value properties
     * @param step              Flow step
     *
     * @return Property store contains all response data
     */
    default PropertyStore<IPropertyModel> deserialize(Map<String, ?> deserializeValues,
                                                      FlowStep step) {
        PropertyStore<IPropertyModel> store = new PropertyStore<>();
        deserializeValues.entrySet().stream()
                         .map(entry -> compose(step, entry.getKey(), entry.getValue()))
                         .filter(Objects::nonNull).forEach(store::add);
        return store;
    }

    /**
     * Compute {@code key} and {@code value} to {@code Property}.
     *
     * @param step  Flow step
     * @param key   Property key
     * @param value Property value
     *
     * @return Default property by given key and value
     *
     * @see IPropertyModel
     * @see PropertyModel
     */
    default IPropertyModel compose(FlowStep step, String key, Object value) {
        IPropertyModel property = this.getPropertyStore().by(step, key);
        if (Objects.isNull(property)) {
            return new PropertyModel(this.getPropertyStore().getVersion(), key).setValue(value);
        }
        return property.duplicate(value);
    }

    /**
     * Property store to convert.
     *
     * @return property store
     */
    T getPropertyStore();

}
