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
    private Object defaultValue;
    @ToString.Include
    private Constraint constraint = Constraint.OPTIONAL;

    /**
     * Init default property value.
     *
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T defaultValue(Object value) {
        this.defaultValue = value;
        return (T) this;
    }

    /**
     * Mark property is required to further error validation.
     *
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T require() {
        this.constraint = Constraint.REQUIRED;
        return (T) this;
    }

    /**
     * Mark property is recommendation to further warn validation.
     *
     * @return Current instance
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T recommend() {
        this.constraint = Constraint.RECOMMENDATION;
        return (T) this;
    }

    /**
     * For internal process to extract {@code PropertyModel} to make request.
     *
     * @param constraint Override constraint
     *
     * @return a clone of {@code PropertyModel}
     */
    protected PropertyModel clone(Constraint constraint) {
        return this.duplicate().constraint(constraint);
    }

    @SuppressWarnings("unchecked")
    protected <T extends PropertyModel> T constraint(Constraint constraint) {
        this.constraint = Objects.requireNonNull(constraint);
        return (T) this;
    }

    public Object getValue() {
        return Objects.isNull(this.value) ? this.defaultValue : this.value;
    }

    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T setValue(Object value) {
        this.value = value;
        return (T) this;
    }

    public <T extends PropertyModel> T duplicate() {
        return new PropertyModel(getVersion(), getName()).constraint(this.constraint)
                                                         .setValue(this.getValue());
    }

    public <T extends PropertyModel> T duplicate(Object value) {
        T instance = this.duplicate();
        return instance.setValue(value);
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

    @Override
    public Object validate() {
        if (Objects.isNull(this.getValue()) || Strings.isBlank(this.getValue().toString())) {
            if (this.isRequired()) {
                throw new OAuthParameterException(
                        "Missing required of property name: " + this.getName());
            }
            if (this.isRecommendation()) {
                log.warn("It is recommendation to add property '{}' when sending OAuth request. " +
                         "Check REST APIs docs for more details.", this.getName());
            }
        }
        return this.getValue();
    }

}
