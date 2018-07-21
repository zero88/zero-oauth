package com.zero.oauth.client.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PropertyList<P extends PropertyModel> {

    private final Map<String, P> props = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <L extends PropertyList<P>> L addProp(P prop) {
        Objects.requireNonNull(prop, "Property cannot be null");
        this.props.put(prop.getName(), prop);
        return (L) this;
    }

    public void delProp(String propName) {
        this.props.remove(propName);
    }

    public void delProp(P prop) {
        this.props.remove(prop.getName());
    }

    public void clear() {
        this.props.clear();
    }

    public P getProp(String propName) {
        return this.props.get(propName);
    }

    public boolean hasProp(String propName) {
        return this.props.containsKey(propName);
    }

    protected Collection<P> getProps() {
        return this.props.values();
    }
}
