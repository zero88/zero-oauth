package com.zero.oauth.client.core;

import java.util.List;

import com.zero.oauth.client.type.FlowStep;

public interface IPropertiesFilter {

    public List<IPropertyModel> by(FlowStep step);

}
