package com.zero.oauth.client.core.properties.converter;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.IPropertyStore;
import com.zero.oauth.client.core.properties.OAuthProperties;
import com.zero.oauth.client.type.FlowStep;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JsonConverter<T extends OAuthProperties> implements PropertiesConverter<T> {

    private final T propertyStore;

    @Override
    public String serialize(FlowStep step) {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map = this.propertyStore.by(step).stream().collect(
            Collectors.toMap(IPropertyModel::getName, IPropertyModel::validate));
        return gson.toJson(map);
    }

    @Override
    public IPropertyStore<IPropertyModel> deserialize(String properties, FlowStep step) {
        Gson gson = new GsonBuilder().create();
        Map<String, Object> map =
            gson.fromJson(properties, new TypeToken<Map<String, Object>>() {}.getType());
        return this.deserialize(map, step);
    }

}
