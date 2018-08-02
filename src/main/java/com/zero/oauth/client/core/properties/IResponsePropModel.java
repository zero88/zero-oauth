package com.zero.oauth.client.core.properties;

/**
 * Represents for {@code PropertyModel} extension, add more attributes to differentiate between HTTP Response
 * success and HTTP Response error.
 */
public interface IResponsePropModel extends IPropertyModel {

    /**
     * Mark property is response error.
     *
     * @return itself
     */
    <T extends IResponsePropModel> T error();

    /**
     * Check response property is error property or success property.
     *
     * @return {@code True} if property is in HTTP response error.
     */
    boolean isError();

}
