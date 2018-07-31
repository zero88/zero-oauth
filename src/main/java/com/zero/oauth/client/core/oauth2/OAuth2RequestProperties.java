package com.zero.oauth.client.core.oauth2;

import java.util.Objects;

import com.zero.oauth.client.type.GrantType;

public final class OAuth2RequestProperties extends OAuth2Properties<OAuth2RequestProp> {

    private OAuth2RequestProperties(GrantType grantType) {
        super(grantType, OAuth2RequestProp.class);
    }

    public static OAuth2RequestProperties init(GrantType grantType) {
        OAuth2RequestProperties props = new OAuth2RequestProperties(grantType);
        if (Objects.nonNull(grantType.getResponseType())) {
            props.update(OAuth2RequestProp.RESPONSE_TYPE.getName(), grantType.getResponseType());
        }
        if (Objects.nonNull(grantType.getType())) {
            props.update(OAuth2RequestProp.GRANT_TYPE.getName(), grantType.getType());
        }
        return props;
    }

}
