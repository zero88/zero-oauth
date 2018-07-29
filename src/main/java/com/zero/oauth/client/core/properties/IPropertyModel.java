package com.zero.oauth.client.core.properties;

import com.zero.oauth.client.exceptions.OAuthParameterException;

/**
 * Define property that is used in request parameter, request header, response.
 */
public interface IPropertyModel {

    /**
     * Property constraint
     */
    public enum Constraint {
        OPTIONAL, REQUIRED, RECOMMENDATION;
    }

    /**
     * Property's name is mandatory and unique, that declares a key in HTTP Request/Response parameters/header or body.
     *
     * @return {@code property's name}
     */
    public String getName();

    /**
     * Property's value is mandatory in case of it is marked as {@link Constraint#REQUIRED}
     *
     * @return {@code property's value}
     */
    public Object getValue();

    /**
     * Validate before serialize or after deserialize.
     *
     * @return {@code property's value}
     * @throws OAuthParameterException
     *         If property is marked as {@link Constraint#REQUIRED} but {@code value} is {@code null} or {@code empty}
     */
    public Object validate();

    /**
     * @param value
     *        Any value but have to implement {@link Object#toString()}
     */
    public <T extends PropertyModel> T setValue(Object value);

    /**
     * Check property is required or not.
     *
     * @return {@code True} if it is marked as {@link Constraint#REQUIRED}
     */
    public boolean isRequired();

    /**
     * Check property is recommendation or not.
     *
     * @return {@code True} if it is marked as {@link Constraint#RECOMMENDATION}
     */
    public boolean isRecommendation();

    /**
     * Check property is required or not.
     *
     * @return {@code True} if it is marked as {@link Constraint#OPTIONAL}
     */
    public boolean isOptional();

}
