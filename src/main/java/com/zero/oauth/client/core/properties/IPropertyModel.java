package com.zero.oauth.client.core.properties;

import com.zero.oauth.client.exceptions.OAuthParameterException;

/**
 * Defines an OAuth property in HTTP request, HTTP response or HTTP header.
 */
public interface IPropertyModel {

    /**
     * Property's name is mandatory and unique, that declares a key in HTTP Request/Response
     * parameters/header or body.
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
     * Validate before serialize or after deserialize.
     *
     * @return {@code property's value}
     *
     * @throws OAuthParameterException If property is marked as {@link Constraint#REQUIRED} but
     *                                 {@code value} is {@code null} or {@code empty}
     */
    Object validate();

    /**
     * Set value.
     *
     * @param value Any value but have to implement {@link Object#toString()}
     *
     * @return
     */
    <T extends PropertyModel> T setValue(Object value);

    <T extends PropertyModel> T duplicate();

    /**
     * Clone current instance with overriding property value. It is helper method to generate new
     * {@code PropertyModel} instance from builtin {@code PropertyModel}.
     *
     * @param value Any value but have to implement {@link Object#toString()}
     *
     * @return New instance
     */
    <T extends PropertyModel> T duplicate(Object value);

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
