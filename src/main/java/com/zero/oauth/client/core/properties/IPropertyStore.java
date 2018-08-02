package com.zero.oauth.client.core.properties;

/**
 * Store all properties that used in one OAuth session.
 *
 * @param <P> Indicates that for this instantiation of the store, the type of {@code IPropertyModel}
 *            implementation.
 * @see IPropertyModel
 * @since 1.0.0
 */
public interface IPropertyStore<P extends IPropertyModel> {

    /**
     * Add new property in store.
     *
     * @param property Property
     */
    void add(P property);

    /**
     * Update property's value.
     *
     * @param name  Property's name
     * @param value Property's value
     */
    void update(String name, Object value);

    /**
     * Remove property out of store.
     *
     * @param name Property's name
     * @return {@link IPropertyModel} instance or {@code null} if store doesn't contain property
     */
    P remove(String name);

    /**
     * Clear all properties in store.
     */
    void clear();

    /**
     * Get property in store.
     *
     * @param name Property's name
     * @return {@link IPropertyModel} instance or {@code null} if store doesn't contain property
     */
    P get(String name);

    /**
     * Check property in store.
     *
     * @param name Property's name
     * @return {@code True} if store has property
     */
    boolean has(String name);

}
