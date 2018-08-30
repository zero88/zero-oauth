package com.zero.oauth.core.oauth1;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.core.properties.IRequestProperties;
import com.zero.oauth.core.properties.PropertyStore;
import com.zero.oauth.core.type.FlowStep;
import com.zero.oauth.core.type.OAuthVersion;
import com.zero.oauth.core.utils.Reflections;

abstract class OAuth1Properties<P extends OAuth1PropertyMatcher> extends PropertyStore<P>
    implements IRequestProperties<P> {

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
