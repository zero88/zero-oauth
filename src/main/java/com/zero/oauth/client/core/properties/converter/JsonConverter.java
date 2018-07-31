package com.zero.oauth.client.core.properties.converter;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.type.FlowStep;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonConverter<T extends IPropertiesFilter> implements IPropertiesConverter<T> {

    private final T properties;

    @Override
    public T getPropertyStore() {
        return this.properties;
    }

    @Override
    public PropertyStore<IPropertyModel> deserialize(String properties, FlowStep step) {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map =
                gson.fromJson(properties, new TypeToken<Map<String, Object>>() {}.getType());
        return this.deserialize(map, step);
    }

    @Override
    public String serialize(FlowStep step) {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = this.getPropertyStore().by(step).stream().collect(
                Collectors.toMap(IPropertyModel::getName, IPropertyModel::validate));
        return gson.toJson(map);
    }

}
