package com.zero.oauth.core.properties;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Response OAuth Properties.
 *
 * @param <R> Type of Response Property model
 * @see IResponseProperty
 */
public interface IResponseProperties<R extends IResponseProperty> extends IRequestProperties<R> {

    /**
     * Identify the HTTP Response whether is error or not. Normally, it is depends on HTTP status codes.
     *
     * @return {@code True} if HTTP status code is greater than or equal to {@code 400}, else otherwise.
     */
    boolean isError();

    /**
     * Mark store is error.
     */
    void markToError();

    /**
     * Get a list of error property.
     *
     * @return error properties
     */
    default List<R> errors() {
        return this.properties().stream().filter(R::isError).collect(Collectors.toList());
    }

    default Map<String, Object> toMap() {
        return this.properties().stream().collect(Collectors.toMap(IPropertyModel::getName, IPropertyModel::getValue));
    }

}
