package com.zero.oauth.client.core;

import java.util.List;

import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

public interface IOAuthPropFilter {

    public List<PropertyModel> by(FlowStep step);

    public List<PropertyModel> by(GrantType grantType, FlowStep step);

}
