package com.zero.oauth.client.core.oauth2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.client.core.properties.PropertyModel;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.GrantType;
import com.zero.oauth.client.type.OAuthVersion;

class OAuth2PropertyModel extends PropertyModel implements IOAuth2PropertyMatcher {

    private final Map<GrantType, Map<FlowStep, Constraint>> mapping = new HashMap<>();

    OAuth2PropertyModel(String name) {
        super(OAuthVersion.V2, name);
    }

    @SuppressWarnings("unchecked")
    public <T extends OAuth2PropertyModel> T declare(GrantType grantType, FlowStep step, Constraint constraint) {
        validate(grantType, step);
        Map<FlowStep, Constraint> flows = this.mapping.get(grantType);
        if (flows == null) {
            flows = new HashMap<>();
            this.mapping.put(grantType, flows);
        }
        flows.put(step, constraint);
        return (T) this;
    }

    /**
     * Check this is matched with the given {@code GrantType} and {@code FlowStep}.
     *
     * @param grantType
     *        {@link GrantType}
     * @param step
     *        {@link FlowStep}
     * @return Custom Property Model
     */
    public PropertyModel match(GrantType grantType, FlowStep step) {
        validate(grantType, step);
        Map<FlowStep, Constraint> flows = this.mapping.get(grantType);
        if (flows == null) {
            return null;
        }
        if (!flows.containsKey(step)) {
            return null;
        }
        try {
            return this.clone(flows.get(step));
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public OAuth2PropertyModel match(GrantType grantType) {
        return this.mapping.containsKey(grantType) ? this : null;
    }

    /**
     * Required value depends on grant type and step. It is not capable to use this method.
     *
     * @deprecated Use {@link #declare(GrantType, FlowStep)}
     * @throws UnsupportedOperationException
     */
    @Override
    public <T extends PropertyModel> T require() {
        throw new UnsupportedOperationException("Required value depends on grant type and step.");
    }

    /**
     * Recommendation value depends on grant type and step. It is not capable to use this method.
     *
     * @deprecated Use {@link #declare(GrantType, FlowStep, Constraint)}
     * @throws UnsupportedOperationException
     */
    @Override
    public <T extends PropertyModel> T recommend() {
        throw new UnsupportedOperationException("Recommendation value depends on grant type and step.");
    }

    private void validate(GrantType grantType, FlowStep step) {
        Objects.requireNonNull(grantType, "OAuth v2.0 grant type cannot be null");
        Objects.requireNonNull(step, "OAuth v2.0 flow step cannot be null");
        if (!grantType.getSteps().contains(step)) {
            throw new OAuthParameterException("Grant type " + grantType.name() + " isn't supported in OAuth flow step " + step);
        }
        if (!this.getVersion().isEqual(step.getVersion())) {
            throw new OAuthParameterException("Step " + step.name() + " isn't supported in OAuth v" + this.getVersion());
        }
    }
}
