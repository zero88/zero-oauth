package com.zero.oauth.core.oauth2;

import java.util.Objects;

import com.zero.oauth.core.type.GrantType;

public final class OAuth2RequestProperties extends OAuth2Properties<OAuth2RequestProperty> {

    private OAuth2RequestProperties(GrantType grantType) {
        super(grantType, OAuth2RequestProperty.class);
    }

    public static OAuth2RequestProperties init(GrantType grantType) {
        OAuth2RequestProperties props = new OAuth2RequestProperties(grantType);
        if (Objects.nonNull(grantType.getResponseType())) {
            props.update(OAuth2RequestProperty.RESPONSE_TYPE.getName(), grantType.getResponseType());
        }
        if (Objects.nonNull(grantType.getType())) {
            props.update(OAuth2RequestProperty.GRANT_TYPE.getName(), grantType.getType());
        }
        return props;
    }

    @Override
    public OAuth2RequestProperty create(String name) {
        return new OAuth2RequestProperty(name);
    }

}
