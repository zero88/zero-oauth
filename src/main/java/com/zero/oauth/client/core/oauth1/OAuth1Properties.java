package com.zero.oauth.client.core.oauth1;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.core.properties.RequestProperties;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.OAuthVersion;
import com.zero.oauth.client.utils.Reflections;

class OAuth1Properties<P extends OAuth1PropertyMatcher> extends PropertyStore<P> implements RequestProperties<P> {

    OAuth1Properties(Class<P> clazz) {
        this.init(Reflections.getConstants(clazz));
    }

    @SuppressWarnings("unchecked")
    @Override
    public final List<P> by(FlowStep step) {
        return this.properties().stream().map(prop -> (P) prop.match(step)).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public final P by(FlowStep step, String name) {
        P property = this.get(name);
        return Objects.isNull(property) ? null : (P) property.match(step);
    }

    @Override
    public final OAuthVersion getVersion() {
        return OAuthVersion.V1;
    }

}
