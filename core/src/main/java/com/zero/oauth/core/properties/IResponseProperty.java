package com.zero.oauth.core.properties;

/**
 * Represents for {@code PropertyModel} extension, add more attributes to differentiate between HTTP Response success
 * and HTTP Response error.
 *
 * @since 1.0.0
 */
public interface IResponseProperty extends IPropertyModel {

    /**
     * Mark response property whether is error or not.
     *
     * @param <T> the type of {@code IPropertyModel} implementation.
     * @return itself
     */
    <T extends IResponseProperty> T error();

    /**
     * Check response property is error property or success property.
     *
     * @return {@code True} if property is in HTTP response error.
     */
    boolean isError();

}
