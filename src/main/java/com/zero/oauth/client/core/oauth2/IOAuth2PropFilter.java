package com.zero.oauth.client.core.oauth2;

import com.zero.oauth.client.core.IPropertyModel;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;

public interface IOAuth2PropFilter extends IPropertyModel {

    /**
     * Check this is matched with the given {@code GrantType} and {@code FlowStep}.
     *
     * @param grantType
     *        {@link GrantType}
     * @param step
     *        {@link FlowStep}
     * @return Custom Property Model
     */
    IPropertyModel check(GrantType grantType, FlowStep step);

    /**
     * Check this is matched with the given {@code GrantType}.
     *
     * @param grantType
     *        {@link GrantType}
     * @return Custom Property Model
     */
    <T extends IPropertyModel> T by(GrantType grantType);

}
