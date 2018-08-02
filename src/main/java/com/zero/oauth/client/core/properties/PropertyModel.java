package com.zero.oauth.client.core.properties;

import java.util.Objects;

import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.OAuthVersion;
import com.zero.oauth.client.utils.Strings;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

/**
 * Default Property.
 */
@Log4j2
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class PropertyModel implements IPropertyModel {

    @Getter
    @ToString.Include
    private final OAuthVersion version;
    @Getter
    @EqualsAndHashCode.Include
    @ToString.Include
    private final String name;
    private Object value;
    @ToString.Include
    private Constraint constraint = Constraint.OPTIONAL;

    /**
     * Mark property is required to further error validation.
     *
     * @param <T> Any type of {@code Property Model}
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T require() {
        this.constraint = Constraint.REQUIRED;
        return (T) this;
    }

    /**
     * Mark property is recommendation to further warn validation.
     *
     * @param <T> Any type of {@code Property Model}
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T recommend() {
        this.constraint = Constraint.RECOMMENDATION;
        return (T) this;
    }

    /**
     * For internal process to extract {@code PropertyModel} to make request.
     *
     * @param constraint Override constraint
     * @return a clone of {@code PropertyModel}
     */
    protected IPropertyModel clone(Constraint constraint) {
        return this.duplicate().constraint(constraint);
    }

    public Object getValue() {
        return this.value;
    }

    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T setValue(Object value) {
        this.value = value;
        return (T) this;
    }

    @Override
    public Object validate() {
        if (Objects.isNull(this.getValue()) || Strings.isBlank(this.getValue().toString())) {
            if (this.isRequired()) {
                throw new OAuthParameterException("Missing required of property name: " + this.getName());
            }
            if (this.isRecommendation()) {
                log.warn("It is recommendation to add property '{}' when sending OAuth request. " +
                         "Check REST APIs docs for more details.", this.getName());
            }
        }
        return this.getValue();
    }

    public <T extends IPropertyModel> T duplicate(Object value) {
        T instance = this.duplicate();
        return instance.setValue(value);
    }

    @SuppressWarnings("unchecked")
    public <T extends IPropertyModel> T constraint(Constraint constraint) {
        this.constraint = Objects.requireNonNull(constraint);
        return (T) this;
    }

    public boolean isRequired() {
        return this.constraint == Constraint.REQUIRED;
    }

    public boolean isRecommendation() {
        return this.constraint == Constraint.RECOMMENDATION;
    }

    public boolean isOptional() {
        return this.constraint == Constraint.OPTIONAL;
    }

    protected <T extends IPropertyModel> T duplicate() {
        return new PropertyModel(getVersion(), getName()).constraint(this.constraint)
                                                         .setValue(this.getValue());
    }

}
