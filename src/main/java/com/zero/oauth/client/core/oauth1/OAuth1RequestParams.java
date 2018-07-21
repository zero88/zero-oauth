package com.zero.oauth.client.core.oauth1;

import java.util.List;

import com.zero.oauth.client.core.IOAuthPropFilter;
import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.core.PropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.utils.ReflectionUtils;

public final class OAuth1RequestParams extends PropertyList<OAuth1RequestParam> implements IOAuthPropFilter {

    OAuth1RequestParams() {
        for (OAuth1RequestParam param : ReflectionUtils.getConstants(OAuth1RequestParam.class)) {
            this.addProp(param);
        }
    }

    @Override
    public List<PropertyModel> by(FlowStep step) {
        return null;
    }

    @Override
    public List<PropertyModel> by(GrantType grantType, FlowStep step) {
        throw new UnsupportedOperationException("It doesn't conform OAuth 1.0a");
    }

}
