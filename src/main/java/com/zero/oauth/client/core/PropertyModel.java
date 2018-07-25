package com.zero.oauth.client.core;

import java.util.Objects;

import com.zero.oauth.client.type.OAuthVersion;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code PropertyModel} is model to define an OAuth property in HTTP request, HTTP response or HTTP header.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PropertyModel implements Cloneable, IPropertyModel {

    @Getter
    private final OAuthVersion version;
    @Getter
    @EqualsAndHashCode.Include
    private final String name;
    private Object value;
    private Object defaultValue;
    private Constraint constraint = Constraint.OPTIONAL;

    public boolean isRequired() {
        return this.constraint == Constraint.REQUIRED;
    }

    public boolean isRecommendation() {
        return this.constraint == Constraint.RECOMMENDATION;
    }

    public boolean isOptional() {
        return this.constraint == Constraint.OPTIONAL;
    }

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
     * @param value
     *        Any value but have to implement {@link Object#toString()}
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T setValue(Object value) {
        this.value = value;
        return (T) this;
    }

    public Object getValue() {
        return Objects.isNull(this.value) ? this.defaultValue : this.value;
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
     * @return a clone of {@code PropertyModel}
     */
    public PropertyModel clone() throws CloneNotSupportedException {
        return (PropertyModel) super.clone();
    }

    /**
     * Clone current instance with overriding param value. It is helper method to
     * generate new {@code PropertyModel} instance from builtin {@code PropertyModel}.
     *
     * @param value
     *        Override param value for clone object.
     * @return a clone of {@code PropertyModel}
     * @throws CloneNotSupportedException
     */
    @SuppressWarnings("unchecked")
    public <T extends PropertyModel> T clone(Object value) throws CloneNotSupportedException {
        T instance = (T) this.clone();
        instance.setValue(value);
        return instance;
    }

    /**
     * For internal process to extract {@code PropertyModel} to make request.
     *
     * @param constraint
     * @return a clone of {@code PropertyModel}
     * @throws CloneNotSupportedException
     */
    protected PropertyModel clone(Constraint constraint) throws CloneNotSupportedException {
        PropertyModel instance = this.clone();
        instance.constraint(constraint);
        return instance;
    }

    @SuppressWarnings("unchecked")
    protected <T extends PropertyModel> T constraint(Constraint constraint) {
        this.constraint = Objects.requireNonNull(constraint);
        return (T) this;
    }

}
