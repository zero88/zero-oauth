package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.Reflections;

import lombok.Getter;

@Getter
public final class OAuth2ResponseProperties extends OAuth2Properties<OAuth2ResponseProperty> {

    private final boolean error;

    private OAuth2ResponseProperties(GrantType grantType) {
        super(grantType, OAuth2ResponseProperty.class);
        this.error = false;
    }

    private OAuth2ResponseProperties(GrantType grantType, boolean error) {
        super(grantType);
        for (OAuth2ResponseProperty prop : Reflections.getConstants(OAuth2ResponseProperty.class)) {
            if (prop.isError()) {
                this.add(prop);
            }
        }
        this.error = error;
    }

    public static OAuth2ResponseProperties init(GrantType grantType) {
        return new OAuth2ResponseProperties(grantType);
    }

    public static OAuth2ResponseProperties initError(GrantType grantType) {
        return new OAuth2ResponseProperties(grantType, true);
    }

}
