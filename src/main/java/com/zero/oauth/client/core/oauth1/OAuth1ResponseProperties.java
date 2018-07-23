package com.zero.oauth.client.core.oauth1;

import java.util.List;

import com.zero.oauth.client.core.IPropertiesFilter;
import com.zero.oauth.client.core.IPropertyModel;
import com.zero.oauth.client.core.PropertyList;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.utils.ReflectionUtils;

public class OAuth1ResponseProperties extends PropertyList<OAuth1ResponseProp> implements IPropertiesFilter {

    OAuth1ResponseProperties() {
        for (OAuth1ResponseProp prop : ReflectionUtils.getConstants(OAuth1ResponseProp.class)) {
            this.addProp(prop);
        }
    }

    @Override
    public List<IPropertyModel> by(FlowStep step) {
        return null;
    }

}
