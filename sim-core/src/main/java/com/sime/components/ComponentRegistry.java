package com.sime.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ComponentRegistry {

    Map<Class<? extends Component>, Map<Integer, Component>> registry;
    
    public ComponentRegistry() {
        this.registry = new HashMap<>();
    }

    public <T extends Component> T get(int id, Class<T> type) {
        var map = registry.get(type);
        if (map == null) return null;
        return type.cast(map.get(id));
    }

    public <T extends Component> void put(int entityId, T component) {
        registry.computeIfAbsent(component.getClass(), k -> new HashMap<>())
                .put(entityId, component);
    }

    public <T extends Component> int remove(int id, Class<T> type) {
        var map = registry.get(type);
        if (map.containsKey(id)) {
            map.remove(id);
            return id;
        }

        return -1;
    }
    
    public int destroy(int id) {
        var entityComponents = listEntityComponents(id);

        for(var component : entityComponents) {
            remove(id, component);
        }

        return id;
    }

    public List<Class<? extends Component>> listEntityComponents(int id) {
        List<Class<? extends Component>> entityComponents = new ArrayList<>();

        for(var component : registry.keySet()) {
            if (has(id, component)) {
                entityComponents.add(component);
            }
        }

        return entityComponents;

    }

    public <T extends Component> boolean has(int entityId, Class<T> type) {
        var map = registry.get(type);
        return map != null && map.containsKey(entityId);
    }

    public <T extends Component> Map<Integer, T> view(Class<T> type) {
        var map = registry.get(type);
        if (map == null) return Map.of();
        @SuppressWarnings("unchecked")
        Map<Integer, T> typed = (Map<Integer, T>) (Map<?, ?>) map;
        return typed;
    }

    public Iterable<Integer> entitiesWith(List<Class<? extends Component>> types) {
        if (types.isEmpty()) return List.of();

        // Find smallest store to iterate
        Map<Integer, Component> smallest = null;
        for (var t : types) {
            var m = registry.get(t);
            if (m == null) return List.of();
            if (smallest == null || m.size() < smallest.size()) {
                smallest = m;
            }
        }

        List<Integer> result = new ArrayList<>();
        outer:
        for (int id : smallest.keySet()) {
            for (var t : types) {
                if (!registry.get(t).containsKey(id)) {
                    continue outer;
                }
            }
            result.add(id);
        }

        return result;
    }

}
