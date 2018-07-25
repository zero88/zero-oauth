package com.zero.oauth.client.core;

/**
 * Define property that is used in request parameter, request header, response
 */
public interface IPropertyModel {

    public enum Constraint {
        OPTIONAL, REQUIRED, RECOMMENDATION
    }

    String getName();

    Object getValue();

    public <T extends PropertyModel> T setValue(Object value);

    public boolean isRequired();

    public boolean isRecommendation();

    public boolean isOptional();

}
