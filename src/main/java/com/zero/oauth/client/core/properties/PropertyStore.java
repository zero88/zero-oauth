package com.zero.oauth.client.core.properties;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zero.oauth.client.utils.Strings;

/**
 * Default Property Store.
 */
public class PropertyStore<P extends IPropertyModel> implements IPropertyStore<P> {

    private final Map<String, P> defaultProps = new HashMap<>();
    private final Map<String, P> actualProps = new HashMap<>();

    protected void init(List<P> defaultProps) {
        defaultProps.forEach(property -> this.addMore(this.defaultProps, property));
    }

    private void addMore(Map<String, P> properties, P property) {
        Objects.requireNonNull(property, "Property cannot be null");
        Strings.requireNotBlank(property.getName(), "Property name cannot be blank");
        properties.put(property.getName(), property);
    }

    protected Collection<P> properties() {
        Collector<Entry<String, P>, ?, Map<String, P>> map =
            Collectors.toMap(Entry::getKey, Entry::getValue, (actual, prop) -> actual);
        Map<String, P> properties =
            Stream.of(this.actualProps, this.defaultProps).map(Map::entrySet).flatMap(Collection::stream)
                  .collect(map);
        return properties.values();
    }

    public void add(P property) {
        addMore(actualProps, property);
    }

    @SuppressWarnings("unchecked")
    public void update(String name, Object value) {
        P prop = this.get(name);
        if (Objects.isNull(prop)) {
            P property = this.defaultProps.get(name);
            Objects.requireNonNull(property, "Not found property name: " + name);
            this.add((P) property.duplicate(value));
        } else {
            prop.setValue(value);
        }
    }

    public P remove(String name) {
        return this.actualProps.remove(name);
    }

    public void clear() {
        this.actualProps.clear();
    }

    public P get(String name) {
        return this.actualProps.get(name);
    }

    public boolean has(String name) {
        return this.actualProps.containsKey(name);
    }

}
