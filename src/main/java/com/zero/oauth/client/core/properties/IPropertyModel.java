package com.zero.oauth.client.core.properties;

import java.util.Arrays;
import java.util.List;

import com.zero.oauth.client.exceptions.OAuthParameterException;
import com.zero.oauth.client.type.HttpPlacement;

/**
 * Defines an OAuth property in HTTP request, HTTP response or HTTP header.
 *
 * @since 1.0.0
 */
public interface IPropertyModel {

    /**
     * Property's name is mandatory and unique, that declares a key in HTTP Request/Response parameters/header
     * or body.
     *
     * @return {@code property's name}
     */
    String getName();

    /**
     * Property's value is mandatory in case of it is marked as {@link Constraint#REQUIRED}.
     *
     * @return {@code property's value}
     */
    Object getValue();

    /**
     * Set value.
     *
     * @param <T>   Any type of {@code Property Model}
     * @param value Any value but have to implement {@link Object#toString()}
     * @return itself
     */
    <T extends IPropertyModel> T setValue(Object value);

    /**
     * Validate before serialize or after deserialize.
     *
     * @return {@code property's value}
     * @throws OAuthParameterException If property is marked as {@link Constraint#REQUIRED} but {@code value}
     *                                 is {@code null} or {@code empty}
     */
    Object validate();

    /**
     * Clone current instance with overriding property value. It is helper method to generate new {@code
     * PropertyModel} instance from builtin {@code PropertyModel}.
     *
     * @param <T>   Any type of {@code Property Model}
     * @param value Any value but have to implement {@link Object#toString()}
     * @return New instance
     */
    <T extends IPropertyModel> T duplicate(Object value);

    /**
     * Available HTTP placements that this property can be put into.
     *
     * @return List of available placements
     */
    default List<HttpPlacement> getAvailabePlacements() {
        return Arrays.asList(HttpPlacement.HEADER, HttpPlacement.URI_QUERY, HttpPlacement.BODY);
    }

    /**
     * Set constraint.
     *
     * @param <T>   Any type of {@code Property Model}
     * @param value Constraint value
     * @return itself
     */
    <T extends IPropertyModel> T constraint(Constraint value);

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
