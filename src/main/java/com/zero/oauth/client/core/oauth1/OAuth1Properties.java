package com.zero.oauth.client.core.oauth1;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.utils.ReflectionUtils;

class OAuth1Properties<P extends IOAuth1PropertyMatcher> extends PropertyStore<P> implements IPropertiesFilter {

    protected OAuth1Properties(Class<P> clazz) {
        for (P prop : ReflectionUtils.getConstants(clazz)) {
            this.add(prop);
        }
    }

    @Override
    public List<IPropertyModel> by(FlowStep step) {
        return this.properties().parallelStream().map(prop -> prop.match(step)).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
