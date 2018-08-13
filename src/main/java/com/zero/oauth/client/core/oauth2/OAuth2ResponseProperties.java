package com.zero.oauth.client.core.oauth2;

import java.util.List;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.properties.ResponseProperties;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.Reflections;

import lombok.Getter;

@Getter
public final class OAuth2ResponseProperties extends OAuth2Properties<OAuth2ResponseProperty>
    implements ResponseProperties<OAuth2ResponseProperty> {

    private final boolean error;

    private OAuth2ResponseProperties(GrantType grantType) {
        super(grantType, OAuth2ResponseProperty.class);
        this.error = false;
    }

    private OAuth2ResponseProperties(GrantType grantType, boolean error) {
        super(grantType);
        this.init(Reflections.getConstants(OAuth2ResponseProperty.class).stream()
                             .filter(OAuth2ResponseProperty::isError).collect(Collectors.toList()));
        this.error = error;
    }

    public static OAuth2ResponseProperties init(GrantType grantType) {
        return new OAuth2ResponseProperties(grantType);
    }

    public static OAuth2ResponseProperties initError(GrantType grantType) {
        return new OAuth2ResponseProperties(grantType, true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OAuth2ResponseProperty> errors() {
        return this.properties().stream().filter(OAuth2ResponseProperty::isError).collect(Collectors.toList());
    }

}
