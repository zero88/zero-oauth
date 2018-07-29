package com.zero.oauth.client.core.properties;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.zero.oauth.client.utils.Strings;

/**
 * Store all properties that used in one OAuth session.
 *
 * @param <P>
 *        Indicates that for this instantiation of the store, the type of {@code IPropertyModel} implementation.
 * @see IPropertyModel
 */
public class PropertyStore<P extends IPropertyModel> {

    private final Map<String, P> props = new HashMap<>();

    /**
     * Add new property in store.
     */
    public void add(P property) {
        Objects.requireNonNull(property, "Property cannot be null");
        Strings.requireNotBlank(property.getName(), "Property name cannot be blank");
        this.props.put(property.getName(), property);
    }

    /**
     * Update property's value.
     *
     * @param name
     *        Property's name
     * @param value
     *        Property's value
     */
    public void update(String name, Object value) {
        P prop = this.get(name);
        Objects.requireNonNull(prop, "Not found propery name: " + name);
        prop.setValue(value);
    }

    /**
     * Remove property out of store.
     *
     * @param name
     *        Property's name
     * @return
     *         {@link IPropertyModel} instance or {@code null} if store doesn't contain property
     */
    public P remove(String name) {
        return this.props.remove(name);
    }

    /**
     * Clear all properties in store
     */
    public void clear() {
        this.props.clear();
    }

    /**
     * Get property in store.
     *
     * @param name
     *        Property's name
     * @return
     *         {@link IPropertyModel} instance or {@code null} if store doesn't contain property
     */
    public P get(String name) {
        return this.props.get(name);
    }

    /**
     * Check property in store.
     *
     * @param name
     *        Property's name
     * @return {@code True} if store has property
     */
    public boolean has(String name) {
        return this.props.containsKey(name);
    }

    /**
     * @return
     *         All properties in store.
     */
    protected Collection<P> properties() {
        return this.props.values();
    }
}
