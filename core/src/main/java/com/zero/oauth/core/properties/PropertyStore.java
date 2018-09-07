package com.zero.oauth.core.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zero.oauth.core.type.HttpPlacement;
import com.zero.oauth.core.utils.Strings;

/**
 * Default Property Store.
 */
public abstract class PropertyStore<P extends IPropertyModel> implements IPropertyStore<P> {

    private final Map<String, P> defaultProps = new HashMap<>();
    private final Map<String, P> actualProps = new HashMap<>();
    private final EnumMap<HttpPlacement, List<P>> placementMap = new EnumMap<>(HttpPlacement.class);

    protected void init(List<P> defaultProps) {
        defaultProps.forEach(property -> {
            this.addMore(this.defaultProps, property);
            this.classifyPlacement(property);
        });
    }

    public void add(P property) {
        this.addMore(actualProps, property);
        this.classifyPlacement(property);
    }

    /**
     * {@inheritDoc}
     */
    public void update(String name, Object value) {
        P prop = this.actualProps.get(name);
        if (Objects.isNull(prop)) {
            P property = this.defaultProps.get(name);
            Objects.requireNonNull(property, "Not found property getName: " + name);
            this.add(property.duplicate(value));
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
        return this.actualProps.containsKey(name) ? this.actualProps.get(name) : this.defaultProps.get(name);
    }

    public boolean has(String name) {
        return this.actualProps.containsKey(name) || this.defaultProps.containsKey(name);
    }

    public Collection<P> properties() {
        Map<String, P> properties = Stream.of(this.actualProps, this.defaultProps).map(Map::entrySet).flatMap(
            Collection::stream).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (actual, prop) -> actual));
        return properties.values();
    }

    public Map<String, Object> toMap() {
        return Stream.of(this.actualProps, this.defaultProps).map(Map::entrySet).flatMap(Collection::stream).collect(
            Collectors.toMap(Entry::getKey, entry -> entry.getValue().getValue(), (actual, prop) -> actual));
    }

    private void addMore(Map<String, P> properties, P property) {
        Objects.requireNonNull(property, "Property cannot be null");
        Strings.requireNotBlank(property.getName(), "Property getName cannot be blank");
        properties.put(property.getName(), property);
    }

    private void classifyPlacement(P property) {
        property.getAvailablePlacements().forEach(placement -> {
            this.placementMap.putIfAbsent(placement, new ArrayList<>());
            this.placementMap.get(placement).add(property);
        });
    }

}
