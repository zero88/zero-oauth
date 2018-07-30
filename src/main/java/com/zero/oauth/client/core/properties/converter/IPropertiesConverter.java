package com.zero.oauth.client.core.properties.converter;

import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.PropertyModel;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.type.FlowStep;

import java.util.Map;
import java.util.Objects;

/**
 * Represents {@code converter} that turns transformation properties to data format.
 */
public interface IPropertiesConverter<T extends IPropertiesFilter> {

    public T getPropertyStore();

    public String serialize(FlowStep step);

    public PropertyStore<IPropertyModel> deserialize(String properties, FlowStep step);

    public default PropertyStore<IPropertyModel> deserialize(Map<String, ?> deserializeValues, FlowStep step) {
        PropertyStore<IPropertyModel> store = new PropertyStore<>();
        deserializeValues.entrySet().stream().map(entry -> compute(step, entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull).forEach(prop -> store.add(prop));
        return store;
    }

    public default IPropertyModel compute(FlowStep step, String key, Object value) {
        IPropertyModel property = this.getPropertyStore().by(step, key);
        if (Objects.isNull(property)) {
            return new PropertyModel(this.getPropertyStore().getVersion(), key).setValue(value);
        }
        return property.duplicate(value);
    }
}
