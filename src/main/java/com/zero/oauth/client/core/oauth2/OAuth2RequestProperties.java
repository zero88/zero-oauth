package com.zero.oauth.client.core.oauth2;

import java.util.Objects;

import com.zero.oauth.client.type.GrantType;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class OAuth2RequestProperties extends OAuth2Properties<OAuth2RequestProp> {

    OAuth2RequestProperties(GrantType grantType) {
        super(grantType, OAuth2RequestProp.class);
    }

    public static OAuth2RequestProperties init(GrantType grantType) {
        OAuth2RequestProperties props = new OAuth2RequestProperties(grantType);
        try {
            if (Objects.nonNull(grantType.getResponseType())) {
                props.add(OAuth2RequestProp.RESPONSE_TYPE.clone(grantType.getResponseType()));
            }
            props.add(OAuth2RequestProp.GRANT_TYPE.clone(grantType.getGrantType()));
        } catch (CloneNotSupportedException e) {
            log.debug(e);
        }
        return props;
    }

}
