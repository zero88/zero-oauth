package com.zero.oauth.client.core.properties;

import java.util.List;
import java.util.function.Function;

import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.HttpPlacement;
import com.zero.oauth.client.type.OAuthVersion;

/**
 * Defines an OAuth property in HTTP request, HTTP response or HTTP header.
 *
 * @since 1.0.0
 */
public interface IPropertyModel {

    /**
     * Property's for which OAuth Version.
     *
     * @return {@code OAuth version}
     */
    OAuthVersion getVersion();

    /**
     * Property's name is mandatory and unique, that declares a key in HTTP Request/Response parameters/header
     * or body.
     *
     * @return {@code property's name}
     */
    String getName();

    /**
     * Property's raw value.
     *
     * @return {@code property's value}
     */
    Object getValue();

    /**
     * Set value.
     *
     * @param <P>   Any type of {@code Property Model}
     * @param value Any value but have to implement {@link Object#toString()}
     * @return {@code this}
     */
    <P extends IPropertyModel> P setValue(Object value);

    /**
     * Serialize data. It maybe used {@code Function} to compute value if declare it by {@link
     * #registerFunction(Function)}. Also, validate data depends on {@code Constraint}.
     *
     * @return {@code property's value}
     * @throws OAuthParameterException If property is marked as {@link Constraint#REQUIRED} but {@code value}
     *                                 is {@code null} or {@code empty}
     */
    Object serialize();

    /**
     * Clone current instance with overriding property value. It is helper method to generate new {@code
     * PropertyModel} instance from builtin {@code PropertyModel}.
     *
     * @param <P>   Any type of {@code Property Model}
     * @param value Any value but have to implement {@link Object#toString()}
     * @return New instance
     */
    <P extends IPropertyModel> P duplicate(Object value);

    /**
     * Check if this is compute value.
     *
     * @return {@code True} if it is compute value, else otherwise
     */
    boolean isComputeValue();

    /**
     * Register function to compute value in run time when calling {@link #getValue()}.
     *
     * @param <P> Any type of {@code Property Model}
     * @param <T> Type of raw property's value
     * @param <R> Type of function output
     * @return {@code this}
     */
    <P extends IPropertyModel, T, R> P registerFunction(Function<T, R> func);

    /**
     * Register HTTP Placement that this property can appear in {@code HTTP Request}.
     *
     * @param placements available placements
     * @param <P>        Any type of {@code Property Model}
     * @return {@code this}
     */
    <P extends IPropertyModel> P registerPlacements(HttpPlacement... placements);

    /**
     * Available HTTP placements that this property can be put into.
     *
     * @return List of available placements
     */
    List<HttpPlacement> getAvailablePlacements();

    /**
     * Set constraint.
     *
     * @param <P>   Any type of {@code Property Model}
     * @param value Constraint value
     * @return {@code this}
     */
    <P extends IPropertyModel> P constraint(Constraint value);

    /**
     * Check property is required or not.
     *
     * @return {@code True} if it is marked as {@link Constraint#REQUIRED}
     */
    boolean isRequired();

    /**
     * Check property is recommendation or not.
     *
     * @return {@code True} if it is marked as {@link Constraint#RECOMMENDATION}
     */
    boolean isRecommendation();

    /**
     * Check property is required or not.
     *
     * @return {@code True} if it is marked as {@link Constraint#OPTIONAL}
     */
    boolean isOptional();

    /**
     * Defines property constraint.
     */
    enum Constraint {
        OPTIONAL, REQUIRED, RECOMMENDATION
    }

}
