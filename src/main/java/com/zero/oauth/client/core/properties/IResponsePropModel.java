package com.zero.oauth.client.core.properties;

/**
 * Represents for {@code PropertyModel} extension, add more attributes to differentiate between HTTP Response success
 * and HTTP Response error.
 */
public interface IResponsePropModel {

    /**
     * Mark property is response error.
     *
     * @return
     */
    <T extends IResponsePropModel> T error();

    /**
     * @return {@code True} if property is in HTTP response error.
     */
    boolean isError();
}
