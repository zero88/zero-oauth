package com.zero.oauth.client.core.oauth1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.client.core.properties.PropertyModel;
import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.FlowStep;
import com.zero.oauth.client.type.OAuthVersion;

class OAuth1PropertyModel extends PropertyModel implements IOAuth1PropertyMatcher {

    private final Map<FlowStep, Constraint> steps = new HashMap<>();

    OAuth1PropertyModel(String name) {
        super(OAuthVersion.V1, name);
    }

    @SuppressWarnings("unchecked")
    public <T extends OAuth1PropertyModel> T declare(FlowStep step, Constraint constraint) {
        Objects.requireNonNull(step, "OAuth flow step cannot be null");
        if (!this.getVersion().isEqual(step.getVersion())) {
            throw new OAuthParameterException("Step " + step.name() + " isn't supported in OAuth v" + this.getVersion());
        }
        this.steps.put(step, constraint);
        return (T) this;
    }

    @Override
    public OAuth1PropertyModel match(FlowStep step) {
        return this.steps.containsKey(step) ? this : null;
    }

    /**
     * Required value depends on OAuth flow step. It is not capable to use this method.
     *
     * @deprecated Use {@link #declare(FlowStep)}
     * @throws UnsupportedOperationException
     */
    @Override
    public <T extends PropertyModel> T require() {
        throw new UnsupportedOperationException("Required value depends on grant type and step.");
    }

    /**
     * Recommendation value depends on OAuth flow step. It is not capable to use this method.
     *
     * @deprecated Use {@link #declare(FlowStep, Constraint)}
     * @throws UnsupportedOperationException
     */
    @Override
    public <T extends PropertyModel> T recommend() {
        throw new UnsupportedOperationException("Recommendation value depends on grant type and step.");
    }
}
