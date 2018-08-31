package com.zero.oauth.core.properties;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.HttpPlacement;
import com.zero.oauth.core.type.OAuthVersion;

import lombok.Getter;

@Getter
public final class GenericResponseStore extends PropertyStore<GenericResponseProperty>
    implements IResponseProperties<GenericResponseProperty> {

    private final OAuthVersion version;
    private boolean error;

    public GenericResponseStore(OAuthVersion version, EnumMap<HttpPlacement, Map<String, Object>> data) {
        this.version = version;
        this.error = false;
        data.forEach((placement, keyValue) -> keyValue.forEach(
            (key, value) -> this.add(new GenericResponseProperty(version, key).setValue(value))));
    }

    @Override
    public void markToError() {
        this.error = true;
    }

    @Override
    public List<GenericResponseProperty> by(FlowStep step) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public GenericResponseProperty by(FlowStep step, String name) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public GenericResponseProperty create(String name) {
        return new GenericResponseProperty(version, name);
    }

}
