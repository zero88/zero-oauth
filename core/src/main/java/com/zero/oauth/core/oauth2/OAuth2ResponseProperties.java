package com.zero.oauth.core.oauth2;

import java.util.stream.Collectors;

import com.zero.oauth.core.properties.IResponseProperties;
import com.zero.oauth.core.type.GrantType;
import com.zero.oauth.core.utils.Reflections;

import lombok.Getter;

@Getter
public final class OAuth2ResponseProperties extends OAuth2Properties<OAuth2ResponseProperty>
    implements IResponseProperties<OAuth2ResponseProperty> {

    private boolean error;

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

    @Override
    public void markToError() {
        this.error = true;
    }

    @Override
    public OAuth2ResponseProperty create(String name) {
        return new OAuth2ResponseProperty(name);
    }

}
