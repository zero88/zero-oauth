package com.zero.oauth.client.core.oauth2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.properties.IPropertiesFilter;
import com.zero.oauth.client.core.properties.IPropertyModel;
import com.zero.oauth.client.core.properties.PropertyStore;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.ReflectionUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(value = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class OAuth2Properties<P extends IOAuth2PropertyMatcher> extends PropertyStore<P> implements IPropertiesFilter {

    private final GrantType grantType;

    protected OAuth2Properties(GrantType grantType, Class<P> clazz) {
        this.grantType = grantType;
        for (P prop : ReflectionUtils.getConstants(clazz)) {
            OAuth2PropertyModel propByGrant = prop.match(this.getGrantType());
            if (propByGrant != null) {
                this.add(prop);
            }
        }
    }

    @Override
    public List<IPropertyModel> by(FlowStep step) {
        return this.properties().parallelStream().map(prop -> prop.match(this.grantType, step)).filter(Objects::nonNull)
                   .collect(Collectors.toList());
    }

}
