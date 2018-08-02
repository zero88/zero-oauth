package com.zero.oauth.client.core.oauth2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.OAuthProperties;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.type.OAuthVersion;
import com.zero.oauth.client.utils.Reflections;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(value = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class OAuth2Properties<P extends OAuth2PropertyMatcher> extends PropertyStore<P> implements OAuthProperties {

    private final GrantType grantType;

    OAuth2Properties(GrantType grantType, Class<P> clazz) {
        this.grantType = Objects.requireNonNull(grantType);
        this.init(Reflections.getConstants(clazz).stream()
                             .filter(prop -> Objects.nonNull(prop.match(this.grantType)))
                             .collect(Collectors.toList()));
    }

    @Override
    public final List<IPropertyModel> by(FlowStep step) {
        return this.properties().stream().map(prop -> prop.match(this.grantType, step))
                   .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <T extends IPropertyModel> T by(FlowStep step, String name) {
        P property = this.get(name);
        return Objects.isNull(property) ? null : (T) property.match(this.grantType, step);
    }

    @Override
    public final OAuthVersion getVersion() {
        return OAuthVersion.V2;
    }

}
