package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.type.GrantType;

public final class OAuth2ResponseProperties extends OAuth2Properties<OAuth2ResponseProp> {

    OAuth2ResponseProperties(GrantType grantType) {
        super(grantType, OAuth2ResponseProp.class);
    }

    public static OAuth2ResponseProperties init(GrantType grantType) {
        return new OAuth2ResponseProperties(grantType);
    }
}
