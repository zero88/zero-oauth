package com.zero.oauth.client.core.oauth1;

import java.util.List;

import com.zero.oauth.client.core.IPropertiesFilter;
import com.zero.oauth.client.core.IPropertyModel;
import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.utils.ReflectionUtils;

public final class OAuth1RequestProperties extends PropertyList<OAuth1RequestProp> implements IPropertiesFilter {

    OAuth1RequestProperties() {
        for (OAuth1RequestProp prop : ReflectionUtils.getConstants(OAuth1RequestProp.class)) {
            this.addProp(prop);
        }
    }

    @Override
    public List<IPropertyModel> by(FlowStep step) {
        return null;
    }

}
