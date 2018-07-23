package com.zero.oauth.client.core.oauth2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.IPropertiesFilter;
import com.zero.oauth.client.core.IPropertyModel;
import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.ReflectionUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(value = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class OAuth2Properties<P extends IOAuth2PropFilter> extends PropertyList<P> implements IPropertiesFilter {

    private final GrantType grantType;

    protected OAuth2Properties(GrantType grantType, Class<P> clazz) {
        this.grantType = grantType;
        for (P prop : ReflectionUtils.getConstants(clazz)) {
            OAuth2PropertyModel propByGrant = prop.by(this.getGrantType());
            if (propByGrant != null) {
                this.addProp(prop);
            }
        }
    }

    @Override
    public List<IPropertyModel> by(FlowStep step) {
        return this.getProps().parallelStream().filter(prop -> Objects.nonNull(prop.check(this.grantType, step)))
                   .collect(Collectors.toList());
    }

}
