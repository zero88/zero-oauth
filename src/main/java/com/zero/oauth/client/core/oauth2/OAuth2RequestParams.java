package com.zero.oauth.client.core.oauth2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.zero.oauth.client.core.IOAuthPropFilter;
import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.core.PropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.ReflectionUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class OAuth2RequestParams extends PropertyList<OAuth2RequestParam> implements IOAuthPropFilter {

    OAuth2RequestParams() {
        for (OAuth2RequestParam param : ReflectionUtils.getConstants(OAuth2RequestParam.class)) {
            this.addProp(param);
        }
    }

    public static OAuth2RequestParams init(GrantType grantType) {
        OAuth2RequestParams params = new OAuth2RequestParams();
        try {
            if (Objects.nonNull(grantType.getResponseType())) {
                params.addProp(OAuth2RequestParam.RESPONSE_TYPE.clone(grantType.getResponseType()));
            }
            params.addProp(OAuth2RequestParam.GRANT_TYPE.clone(grantType.getGrantType()));
        } catch (CloneNotSupportedException e) {
            log.debug(e);
        }
        return params;
    }

    @Override
    public List<PropertyModel> by(GrantType grantType, FlowStep step) {
        return this.getProps().parallelStream().filter(prop -> Objects.nonNull(prop.check(grantType, step))).collect(
                                                                                                                     Collectors.toList());
    }

    @Override
    public List<PropertyModel> by(FlowStep step) {
        return null;
    }
}
