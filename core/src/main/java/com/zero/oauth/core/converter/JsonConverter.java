package com.zero.oauth.core.converter;

import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zero.oauth.core.properties.IPropertyModel;
import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.properties.IResponseProperties;
import com.zero.oauth.core.properties.IResponseProperty;
import com.zero.oauth.core.type.FlowStep;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class JsonConverter implements PropertiesConverter {

    @Override
    public <P extends IPropertyModel, R extends IRequestProperties<P>> String serialize(R requestProperties,
                                                                                      FlowStep step) {
        Gson gson = new GsonBuilder().create();
        Map<java.lang.String, Object> map = requestProperties.by(step).stream().collect(
            Collectors.toMap(P::getName, P::serialize));
        return gson.toJson(map);
    }

    @Override
    public Map<String, Object> deserialize(String properties) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(properties, new TypeToken<Map<String, Object>>() {}.getType());
    }

    @Override
    public <P extends IResponseProperty, T extends IResponseProperties<P>> T deserialize(T store, String properties,
                                                                                         FlowStep step) {
        return this.deserialize(store, this.deserialize(properties), step);
    }

}
